package com.kopylov.movieland.service;

import com.kopylov.movieland.dto.MovieDto;
import com.kopylov.movieland.entity.CurrencyType;
import com.kopylov.movieland.entity.Movie;
import com.kopylov.movieland.entity.SortOrder;

import java.util.List;
import java.util.Optional;


public interface MovieService {

    List<Movie> findAll(SortOrder rating,SortOrder price);

    List<Movie> findRandom();

    List<Movie> findByGenre(Long genreId, SortOrder rating, SortOrder price);

    MovieDto findById(long movieId, CurrencyType currencyType);
}
