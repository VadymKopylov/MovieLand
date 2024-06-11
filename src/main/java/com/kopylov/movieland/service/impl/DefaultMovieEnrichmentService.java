package com.kopylov.movieland.service.impl;

import com.kopylov.movieland.dto.CountryDto;
import com.kopylov.movieland.dto.GenreDto;
import com.kopylov.movieland.dto.ReviewDto;
import com.kopylov.movieland.dto.movie.MovieEditDto;
import com.kopylov.movieland.dto.movie.MovieFullInfoDto;
import com.kopylov.movieland.entity.Movie;
import com.kopylov.movieland.mapper.CountryMapper;
import com.kopylov.movieland.mapper.GenreMapper;
import com.kopylov.movieland.service.CountryService;
import com.kopylov.movieland.service.GenreService;
import com.kopylov.movieland.service.MovieEnrichmentService;
import com.kopylov.movieland.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
@Profile("Single")
public class DefaultMovieEnrichmentService implements MovieEnrichmentService {

    private final GenreService genreService;
    private final ReviewService reviewService;
    private final CountryService countryService;
    private final CountryMapper countryMapper;
    private final GenreMapper genreMapper;

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

    @Override
    public void enrichByIds(Movie movie, MovieEditDto movieEditDto) {
        List<CountryDto> countryDtos = countryService.getAllCountriesByIds(movieEditDto.getCountries());
        List<GenreDto> genreDtos = genreService.getAllGenresByIds(movieEditDto.getGenres());

        movie.setCountries(countryMapper.toCountryList(countryDtos));
        movie.setGenres(genreMapper.toGenreList(genreDtos));
    }
}
