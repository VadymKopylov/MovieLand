package com.kopylov.movieland.web.controller;

import com.kopylov.movieland.dto.movie.MovieFullInfoDto;
import com.kopylov.movieland.dto.movie.MovieShortInfoDto;
import com.kopylov.movieland.entity.CurrencyType;
import com.kopylov.movieland.entity.SortOrder;
import com.kopylov.movieland.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public List<MovieShortInfoDto> findAllMovies(@RequestParam(name = "rating", required = false) SortOrder ratingSortOrder,
                                                 @RequestParam(name = "price", required = false) SortOrder priceSortOrder) {
        return movieService.findAll(ratingSortOrder, priceSortOrder);
    }

    @GetMapping("/random")
    public List<MovieShortInfoDto> findRandomMovies() {
        return movieService.findRandom();
    }

    @GetMapping("/genre/{genreId}")
    public List<MovieShortInfoDto> findMoviesByGenre(@PathVariable Long genreId,
                                                     @RequestParam(name = "rating", required = false) SortOrder ratingSortOrder,
                                                     @RequestParam(name = "price", required = false) SortOrder priceSortOrder) {
        return movieService.findByGenre(genreId, ratingSortOrder, priceSortOrder);
    }

    @GetMapping(path = "/{id}")
    public MovieFullInfoDto findById(@PathVariable(value = "id") int movieId,
                                     @RequestParam(value = "currency", required = false) CurrencyType currencyType) {
        return movieService.findById(movieId, currencyType);
    }
}
