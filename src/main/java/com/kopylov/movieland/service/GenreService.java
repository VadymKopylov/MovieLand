package com.kopylov.movieland.service;

import com.kopylov.movieland.dto.GenreDto;

import java.util.List;

public interface GenreService {

    List<GenreDto> getAll();

    List<GenreDto> getGenresByMovieId(int movieId);

    List<GenreDto> getAllGenresByIds(List<Integer> ids);
}
