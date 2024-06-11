package com.kopylov.movieland.service;

import com.kopylov.movieland.dto.ReviewToSaveDto;
import com.kopylov.movieland.dto.movie.MovieEditDto;
import com.kopylov.movieland.dto.movie.MovieFullInfoDto;
import com.kopylov.movieland.dto.movie.MovieShortInfoDto;
import com.kopylov.movieland.entity.CurrencyType;
import com.kopylov.movieland.entity.SortOrder;

import java.util.List;


public interface MovieService {

    List<MovieShortInfoDto> findAll(SortOrder rating, SortOrder price);

    List<MovieShortInfoDto> findRandom();

    List<MovieShortInfoDto> findByGenre(int genreId, SortOrder rating, SortOrder price);

    MovieFullInfoDto findById(int movieId, CurrencyType currencyType);

    MovieFullInfoDto findInDb(int movieId);

    void addReview(ReviewToSaveDto review, String authHeader);

    void addMovie(MovieEditDto movieEditDto);

    void editMovie(MovieEditDto movieEditDto, int id);


}
