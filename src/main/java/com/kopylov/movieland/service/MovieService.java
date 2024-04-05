package com.kopylov.movieland.service;

import com.kopylov.movieland.dto.MovieDto;
import com.kopylov.movieland.entity.Movie;

import java.util.List;
import java.util.Optional;


public interface MovieService {

    List<Movie> getAll(Optional<String> rating, Optional<String> price);

    List<Movie> getRandomMovie();

    List<Movie> getByGenre(Long genreId, Optional<String> rating, Optional<String> price);

    MovieDto getById(long movieId);
}
