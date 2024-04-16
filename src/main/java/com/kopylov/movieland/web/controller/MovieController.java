package com.kopylov.movieland.web.controller;

import com.kopylov.movieland.dto.MovieDto;
import com.kopylov.movieland.entity.CurrencyType;
import com.kopylov.movieland.entity.SortOrder;
import com.kopylov.movieland.mapper.MovieMapper;
import com.kopylov.movieland.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;
    private final MovieMapper movieMapper;

    @GetMapping
    public List<MovieDto> getAllMovies(@RequestParam(name = "rating", required = false) SortOrder ratingSortOrder,
                                       @RequestParam(name = "price", required = false) SortOrder priceSortOrder) {
        return movieMapper.toDto(movieService.findAll(ratingSortOrder, priceSortOrder));
    }

    @GetMapping("/random")
    public List<MovieDto> getRandomMovies() {
        return movieMapper.toDto(movieService.findRandom());
    }

    @GetMapping("/genre/{genreId}")
    public List<MovieDto> getMoviesByGenre(@PathVariable Long genreId,
                                           @RequestParam(name = "rating", required = false) SortOrder ratingSortOrder,
                                           @RequestParam(name = "price", required = false) SortOrder priceSortOrder) {
        return movieMapper.toDto(movieService.findByGenre(genreId, ratingSortOrder, priceSortOrder));
    }

    @GetMapping(path = "/{id}")
    public MovieDto getById(@PathVariable(value = "id") long movieId,
                            @RequestParam(value = "currency", required = false) CurrencyType currencyType) {
        return movieMapper.toDto(movieService.findById(movieId, currencyType));
    }
}
