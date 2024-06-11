package com.kopylov.movieland.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReviewDto {

    private int id;
    private UserDto user;
    private String text;
}
