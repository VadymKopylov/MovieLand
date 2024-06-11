package com.kopylov.movieland.service.impl;

import com.kopylov.movieland.dto.CountryDto;
import com.kopylov.movieland.dto.GenreDto;
import com.kopylov.movieland.dto.ReviewDto;
import com.kopylov.movieland.dto.movie.MovieEditDto;
import com.kopylov.movieland.dto.movie.MovieFullInfoDto;
import com.kopylov.movieland.entity.Movie;
import com.kopylov.movieland.service.CountryService;
import com.kopylov.movieland.service.GenreService;
import com.kopylov.movieland.service.MovieEnrichmentService;
import com.kopylov.movieland.service.ReviewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

@Service
@AllArgsConstructor
@Profile("Parallel")
@Slf4j
public class ParallelMovieEnrichmentService implements MovieEnrichmentService {

    private final static int TIMEOUT = 5;

    private final GenreService genreService;
    private final ReviewService reviewService;
    private final CountryService countryService;
    private final ExecutorService executorService;

    @Override
    public void enrich(MovieFullInfoDto movieDto, MovieEnrichmentService.EnrichmentType... types) {

        List<MovieEnrichmentService.EnrichmentType> enrichmentTypes = Arrays.asList(types);

        List<Callable<List<?>>> taskList = getCallableList(movieDto, enrichmentTypes);

        try {
            List<Future<List<?>>> futureList = executorService.invokeAll(taskList, TIMEOUT, TimeUnit.SECONDS);

            for (int i = 0; i < futureList.size(); i++) {
                Future<List<?>> future = futureList.get(i);
                if (future.isDone()) {
                    try {
                        List<?> result = future.get();
                        EnrichmentType type = enrichmentTypes.get(i);

                        if (type == EnrichmentType.COUNTRIES) {
                            movieDto.setCountries((List<CountryDto>) result);
                        } else if (type == EnrichmentType.GENRES) {
                            movieDto.setGenres((List<GenreDto>) result);
                        } else if (type == EnrichmentType.REVIEWS) {
                            movieDto.setReviews((List<ReviewDto>) result);
                        }
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }
    }

    @Override
    public void enrichByIds(Movie movie, MovieEditDto movieEditDto) {

    }

    private List<Callable<List<?>>> getCallableList(MovieFullInfoDto movieDto, List<EnrichmentType> enrichmentTypes) {
        List<Callable<List<?>>> taskList = new ArrayList<>();

        if (enrichmentTypes.contains(EnrichmentType.COUNTRIES)) {
            taskList.add(() -> {
                log.info("Starting enrichment for COUNTRIES in thread: {}", Thread.currentThread().getName());
                List<CountryDto> result = countryService.getCountriesByMovieId(movieDto.getId());
                log.info("Finished enrichment for COUNTRIES in thread: {}", Thread.currentThread().getName());
                return result;
            });
        }

        if (enrichmentTypes.contains(EnrichmentType.GENRES)) {
            taskList.add(() -> {
                log.info("Starting enrichment for GENRES in thread: {}", Thread.currentThread().getName());
                List<GenreDto> result = genreService.getGenresByMovieId(movieDto.getId());
                log.info("Finished enrichment for GENRES in thread: {}", Thread.currentThread().getName());
                return result;
            });
        }

        if (enrichmentTypes.contains(EnrichmentType.REVIEWS)) {
            taskList.add(() -> {
                log.info("Starting enrichment for REVIEWS in thread: {}", Thread.currentThread().getName());
                List<ReviewDto> result = reviewService.getReviewsByMovieId(movieDto.getId());
                log.info("Finished enrichment for REVIEWS in thread: {}", Thread.currentThread().getName());
                return result;
            });
        }
        return taskList;
    }
}
