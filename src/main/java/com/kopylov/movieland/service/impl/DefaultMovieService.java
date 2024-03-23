package com.kopylov.movieland.service.impl;

import com.kopylov.movieland.entity.Movie;
import com.kopylov.movieland.repository.MovieRepository;
import com.kopylov.movieland.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DefaultMovieService implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public List<Movie> getAll() {
        return movieRepository.findAll();
    }

    @Override
    public List<Movie> getRandomMovie() {
        return movieRepository.findRandomMovies(3);
    }
}
