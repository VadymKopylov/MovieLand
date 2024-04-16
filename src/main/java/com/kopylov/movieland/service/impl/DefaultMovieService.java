package com.kopylov.movieland.service.impl;

import com.kopylov.movieland.entity.CurrencyType;
import com.kopylov.movieland.entity.Movie;
import com.kopylov.movieland.entity.SortOrder;
import com.kopylov.movieland.exception.NotFoundException;
import com.kopylov.movieland.repository.MovieRepository;
import com.kopylov.movieland.service.CurrencyService;
import com.kopylov.movieland.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DefaultMovieService implements MovieService {

    private final MovieRepository movieRepository;
    private final CurrencyService currencyService;

    @Override
    public List<Movie> findAll(SortOrder ratingSortOrder, SortOrder priceSortOrder) {
        return movieRepository.findAllSorted(ratingSortOrder, priceSortOrder);
    }

    @Override
    public List<Movie> findRandom() {
        return movieRepository.findRandomMovies(3);
    }

    @Override
    public List<Movie> findByGenre(Long genreId, SortOrder ratingSortOrder, SortOrder priceSortOrder) {
        List<Movie> byGenreIdSorted = movieRepository.findByGenreIdSorted(genreId, ratingSortOrder, priceSortOrder);
        if (byGenreIdSorted.isEmpty()) {
            throw new NotFoundException("Not found movies by genre");
        }
        return byGenreIdSorted;
    }

    @Override
    public Movie findById(long movieId, CurrencyType currencyType) {
        Optional<Movie> movie = movieRepository.findById(movieId);

        if (movie.isEmpty()) {
            throw new NotFoundException("Not found movie by id " + movieId);
        }

        Movie movieFromDb = movie.get();

        if (currencyType != null) {
            double convertedPrice = currencyService.convertPrice(movieFromDb.getPrice(), currencyType);
            movieFromDb.setPrice(convertedPrice);
        }
        return movieFromDb;
    }
}
