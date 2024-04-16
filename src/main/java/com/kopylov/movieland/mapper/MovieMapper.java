package com.kopylov.movieland.mapper;

import com.kopylov.movieland.dto.MovieDto;
import com.kopylov.movieland.entity.Movie;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = ReviewMapper.class)
public interface MovieMapper {

    MovieDto toDto(Movie movie);

    Movie toMovie(MovieDto movieDto);

    List<MovieDto> toDto(List<Movie> movies);

    List<Movie> toMovies(List<MovieDto> moviesDto);
}
