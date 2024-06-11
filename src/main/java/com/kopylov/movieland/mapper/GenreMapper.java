package com.kopylov.movieland.mapper;

import com.kopylov.movieland.dto.GenreDto;
import com.kopylov.movieland.entity.Genre;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    GenreDto toDto (Genre genre);

    List<GenreDto> toDtoList (List<Genre> genres);

    List<Genre> toGenreList (List<GenreDto> genresDto);
}