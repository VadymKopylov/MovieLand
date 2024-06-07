package com.kopylov.movieland.service.impl;

import com.kopylov.movieland.dto.ReviewToSaveDto;
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
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

    @Override
    public List<MovieShortInfoDto> findAll(SortOrder ratingSortOrder, SortOrder priceSortOrder) {
        return movieMapper.toShortDto(movieRepository.findAllSorted(ratingSortOrder, priceSortOrder));
    }

    @Override
    public List<MovieShortInfoDto> findRandom() {
        return movieMapper.toShortDto(movieRepository.findRandomMovies(3));
    }

    @Override
    public List<MovieShortInfoDto> findByGenre(Long genreId, SortOrder ratingSortOrder, SortOrder priceSortOrder) {
        List<Movie> byGenreIdSorted = movieRepository.findByGenreIdSorted(genreId, ratingSortOrder, priceSortOrder);
        if (byGenreIdSorted.isEmpty()) {
            throw new NotFoundException("Not found movies by genre");
        }
        return movieMapper.toShortDto(byGenreIdSorted);
    }

    @Override
    public MovieFullInfoDto findById(long movieId, CurrencyType currencyType) {
        Optional<Movie> movie = movieRepository.findById(movieId);

        if (movie.isEmpty()) {
            throw new NotFoundException("Not found movie by id " + movieId);
        }

        Movie movieFromDb = movie.get();

        MovieFullInfoDto movieFullInfoDto = movieMapper.toDtoForEnrichment(movieFromDb);

        movieEnrichmentService.enrich(movieFullInfoDto, COUNTRIES, GENRES, REVIEWS);

        if (currencyType != null) {
            double convertedPrice = currencyService.convertFromUah(movieFromDb.getPrice(), currencyType);
            movieFullInfoDto.setPrice(convertedPrice);
        }
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
}
