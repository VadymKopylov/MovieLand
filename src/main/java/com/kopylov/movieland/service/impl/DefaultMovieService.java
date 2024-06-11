package com.kopylov.movieland.service.impl;

import com.kopylov.movieland.dto.ReviewToSaveDto;
import com.kopylov.movieland.dto.movie.MovieEditDto;
import com.kopylov.movieland.dto.movie.MovieFullInfoDto;
import com.kopylov.movieland.dto.movie.MovieShortInfoDto;
import com.kopylov.movieland.entity.CurrencyType;
import com.kopylov.movieland.entity.Movie;
import com.kopylov.movieland.entity.SortOrder;
import com.kopylov.movieland.exception.NotFoundException;
import com.kopylov.movieland.mapper.MovieMapper;
import com.kopylov.movieland.repository.MovieRepository;
import com.kopylov.movieland.service.CurrencyService;
import com.kopylov.movieland.service.MovieEnrichmentService;
import com.kopylov.movieland.service.MovieService;
import com.kopylov.movieland.service.ReviewService;
import com.kopylov.movieland.util.SoftReferenceCache;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.kopylov.movieland.service.MovieEnrichmentService.EnrichmentType.*;

@Service
@AllArgsConstructor
public class DefaultMovieService implements MovieService {

    private final MovieRepository movieRepository;
    private final CurrencyService currencyService;
    private final MovieEnrichmentService movieEnrichmentService;
    private final MovieMapper movieMapper;
    private final ReviewService reviewService;
    private final SoftReferenceCache movieCacheService;

    @Override
    public List<MovieShortInfoDto> findAll(SortOrder ratingSortOrder, SortOrder priceSortOrder) {
        return movieMapper.toShortDto(movieRepository.findAllSorted(ratingSortOrder, priceSortOrder));
    }

    @Override
    public List<MovieShortInfoDto> findRandom() {
        return movieMapper.toShortDto(movieRepository.findRandomMovies(3));
    }

    @Override
    public List<MovieShortInfoDto> findByGenre(int genreId, SortOrder ratingSortOrder, SortOrder priceSortOrder) {
        List<Movie> byGenreIdSorted = movieRepository.findByGenreIdSorted(genreId, ratingSortOrder, priceSortOrder);
        if (byGenreIdSorted.isEmpty()) {
            throw new NotFoundException("Not found movies by genre");
        }
        return movieMapper.toShortDto(byGenreIdSorted);
    }

    @Override
    public MovieFullInfoDto findById(int movieId, CurrencyType currencyType) {

        MovieFullInfoDto movie = (MovieFullInfoDto) movieCacheService.get(movieId);

        if (currencyType != null) {
            double convertedPrice = currencyService.convertFromUah(movie.getPrice(), currencyType);
            movie.setPrice(convertedPrice);
        }
        return movie;
    }

    public MovieFullInfoDto findInDb(int movieId) {
        Optional<Movie> movie = movieRepository.findById(movieId);

        if (movie.isEmpty()) {
            throw new NotFoundException("Not found movie by id " + movieId);
        }

        Movie movieFromDb = movie.get();

        MovieFullInfoDto movieFullInfoDto = movieMapper.toDtoForEnrichment(movieFromDb);

        movieEnrichmentService.enrich(movieFullInfoDto, COUNTRIES, GENRES, REVIEWS);

        return movieFullInfoDto;

    }

    @Override
    public void addReview(ReviewToSaveDto reviewToSaveDto, String authHeader) {
        Optional<Movie> movieOptional = movieRepository.findById(reviewToSaveDto.getMovieId());
        if (movieOptional.isPresent()) {
            Movie movie = movieOptional.get();
            reviewService.addReview(authHeader, movie, reviewToSaveDto);
        } else {
            throw new NotFoundException("Not found movie by id " + reviewToSaveDto.getMovieId());
        }
    }

    @Override
    public void addMovie(MovieEditDto movieEditDto) {
        Movie movieToSave = movieMapper.toEntity(movieEditDto);
        movieEnrichmentService.enrichByIds(movieToSave, movieEditDto);

        movieRepository.save(movieToSave);
    }

    @Override
    @Transactional
    public void editMovie(MovieEditDto movieEditDto, int movieId) {
        Movie movieForEdit = movieRepository.getReferenceById(movieId);

        if (movieEditDto.getNameRussian() != null) {
            movieForEdit.setNameRussian(movieEditDto.getNameRussian());
        }
        if (movieEditDto.getNameNative() != null) {
            movieForEdit.setNameNative(movieEditDto.getNameNative());
        }
        if (movieEditDto.getYearOfRelease() != null) {
            movieForEdit.setYearOfRelease(movieEditDto.getYearOfRelease());
        }
        if (movieEditDto.getDescription() != null) {
            movieForEdit.setDescription(movieEditDto.getDescription());
        }
        if (movieEditDto.getRating() != 0) {
            movieForEdit.setRating(movieEditDto.getRating());
        }
        if (movieEditDto.getPrice() != 0) {
            movieForEdit.setPrice(movieEditDto.getPrice());
        }
        if (movieEditDto.getPicturePath() != null) {
            movieForEdit.setPicturePath(movieEditDto.getPicturePath());
        }
        if (movieEditDto.getCountries() != null || movieEditDto.getGenres() != null) {
            movieEnrichmentService.enrichByIds(movieForEdit, movieEditDto);
        } else {
            throw new NotFoundException("Not found movie by id " + movieEditDto.getId());
        }
        movieRepository.save(movieForEdit);

        movieCacheService.put(movieId, movieForEdit);
    }
}
