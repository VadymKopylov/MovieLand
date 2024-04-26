package com.kopylov.movieland.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GenreDto {

    private final int id;
    private final String name;
}