package com.kopylov.movieland.mapper;

import com.kopylov.movieland.dto.movie.MovieFullInfoDto;
import com.kopylov.movieland.dto.movie.MovieShortInfoDto;
import com.kopylov.movieland.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = ReviewMapper.class)
public interface MovieMapper {

    MovieFullInfoDto toDto(Movie movie);

    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "countries", ignore = true)
    @Mapping(target = "genres", ignore = true)
    MovieFullInfoDto toDtoForEnrichment(Movie movie);

    List<MovieShortInfoDto> toShortDto(List<Movie> movies);
}
