package com.kopylov.movieland.web.controller;

import com.kopylov.movieland.dto.MovieDto;
import com.kopylov.movieland.entity.CurrencyType;
import com.kopylov.movieland.entity.Movie;
import com.kopylov.movieland.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public List<Movie> getAllMovies(@RequestParam(name = "rating", required = false) Optional<String> ratingSortOrder,
                                    @RequestParam(name = "price", required = false) Optional<String> priceSortOrder) {
        return movieService.findAll(ratingSortOrder, priceSortOrder);
    }

    @GetMapping("/random")
    public List<Movie> getRandomMovies() {
        return movieService.findRandom();
    }

    @GetMapping("/genre/{genreId}")
    public List<Movie> getMoviesByGenre(@PathVariable Long genreId,
                                        @RequestParam(name = "rating", required = false) Optional<String> ratingSortOrder,
                                        @RequestParam(name = "price", required = false) Optional<String> priceSortOrder) {
        return movieService.findByGenre(genreId, ratingSortOrder, priceSortOrder);
    }

    @GetMapping(path = "/{id}")
    public MovieDto getById(@PathVariable(value = "id") long movieId,
                            @RequestParam(value = "currency", required = false) CurrencyType currencyType) {
        return movieService.findById(movieId,currencyType);
    }
}
