package com.kopylov.movieland.web.controller;

import com.kopylov.movieland.entity.Movie;
import com.kopylov.movieland.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public List<Movie> getAllMovies(@RequestParam(name = "rating", required = false) Optional<String> ratingSortOrder,
                                    @RequestParam(name = "price", required = false) Optional<String> priceSortOrder) {
        return movieService.getAll(ratingSortOrder, priceSortOrder);
    }

    @GetMapping("/random")
    public List<Movie> getRandomMovies() {
        return movieService.getRandomMovie();
    }

    @GetMapping("/genre/{genreId}")
    public List<Movie> getMoviesByGenre(@PathVariable Long genreId,
                                        @RequestParam(name = "rating", required = false) Optional<String> ratingSortOrder,
                                        @RequestParam(name = "price", required = false) Optional<String> priceSortOrder) {
        return movieService.getByGenre(genreId, ratingSortOrder, priceSortOrder);
    }
}
