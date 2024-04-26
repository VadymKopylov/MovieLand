package com.kopylov.movieland.service;

import com.kopylov.movieland.dto.GenreDto;

import java.util.List;

public interface Cache {

    List<GenreDto> getAll();
}
