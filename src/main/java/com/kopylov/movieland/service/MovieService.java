package com.kopylov.movieland.service;

import com.kopylov.movieland.entity.Movie;

import java.util.List;


public interface MovieService {

    List<Movie> getAll();

    List<Movie> getRandomMovie();

    List<Movie> getByGenre(Long genreId);
}
