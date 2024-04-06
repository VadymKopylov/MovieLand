package com.kopylov.movieland.service;

import com.kopylov.movieland.dto.MovieDto;
import com.kopylov.movieland.entity.CurrencyType;
import com.kopylov.movieland.entity.Movie;

import java.util.List;
import java.util.Optional;


public interface MovieService {

    List<Movie> findAll(Optional<String> rating, Optional<String> price);

    List<Movie> findRandom();

    List<Movie> findByGenre(Long genreId, Optional<String> rating, Optional<String> price);

    MovieDto findById(long movieId, CurrencyType currencyType);
}
