package com.kopylov.movieland.mapper;

import com.kopylov.movieland.dto.movie.MovieFullInfoDto;
import com.kopylov.movieland.dto.movie.MovieShortInfoDto;
import com.kopylov.movieland.entity.Movie;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = ReviewMapper.class)
public interface MovieMapper {

    MovieFullInfoDto toDto(Movie movie);

    MovieShortInfoDto toShortDto(Movie movie);

    List<MovieFullInfoDto> toDto(List<Movie> movies);

    List<MovieShortInfoDto> toShortDto(List<Movie> movies);
}
