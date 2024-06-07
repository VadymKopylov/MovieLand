package com.kopylov.movieland.service.impl;

import com.kopylov.movieland.dto.CountryDto;
import com.kopylov.movieland.dto.GenreDto;
import com.kopylov.movieland.dto.ReviewDto;
import com.kopylov.movieland.dto.movie.MovieFullInfoDto;
import com.kopylov.movieland.service.CountryService;
import com.kopylov.movieland.service.GenreService;
import com.kopylov.movieland.service.MovieEnrichmentService;
import com.kopylov.movieland.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class DefaultMovieEnrichmentService implements MovieEnrichmentService {

    private final GenreService genreService;
    private final ReviewService reviewService;
    private final CountryService countryService;

    @Override
    public void enrich(MovieFullInfoDto movieDto, EnrichmentType... types) {

        List<EnrichmentType> enrichmentTypes = Arrays.asList(types);

        if (enrichmentTypes.contains(EnrichmentType.COUNTRIES)) {
            List<CountryDto> countries = countryService.getCountriesByMovieId(movieDto.getId());
            movieDto.setCountries(countries);
        }

        if (enrichmentTypes.contains(EnrichmentType.GENRES)) {
            List<GenreDto> genres = genreService.getGenresByMovieId(movieDto.getId());
            movieDto.setGenres(genres);
        }

        if (enrichmentTypes.contains(EnrichmentType.REVIEWS)) {
            List<ReviewDto> reviews = reviewService.getReviewsByMovieId(movieDto.getId());
            movieDto.setReviews(reviews);
        }
    }
}
