package com.kopylov.movieland.web.controller;

import com.kopylov.movieland.entity.Movie;
import com.kopylov.movieland.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAll();
    }

    @GetMapping("/random")
    public List<Movie> getRandomMovies() {
        return movieService.getRandomMovie();
    }
}
