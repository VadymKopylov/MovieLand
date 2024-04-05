package com.kopylov.movieland.web.controller;

import com.kopylov.movieland.dto.MovieDto;
import com.kopylov.movieland.dto.ReviewDto;
import com.kopylov.movieland.dto.UserDto;
import com.kopylov.movieland.entity.Movie;
import com.kopylov.movieland.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping(path = "/movies")
    public List<Movie> getAllMovies(@RequestParam(name = "rating", required = false) Optional<String> ratingSortOrder,
                                    @RequestParam(name = "price", required = false) Optional<String> priceSortOrder) {
        return movieService.getAll(ratingSortOrder, priceSortOrder);
    }

    @GetMapping("/movies/random")
    public List<Movie> getRandomMovies() {
        return movieService.getRandomMovie();
    }

    @GetMapping("/movies/genre/{genreId}")
    public List<Movie> getMoviesByGenre(@PathVariable Long genreId,
                                        @RequestParam(name = "rating", required = false) Optional<String> ratingSortOrder,
                                        @RequestParam(name = "price", required = false) Optional<String> priceSortOrder) {
        return movieService.getByGenre(genreId, ratingSortOrder, priceSortOrder);
    }

    @GetMapping(path = "/movie/{id}")
    public MovieDto getById(@PathVariable(value = "id") long movieId) {
        return movieService.getById(movieId);
    }
}
