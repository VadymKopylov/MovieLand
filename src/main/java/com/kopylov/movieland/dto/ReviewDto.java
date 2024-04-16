package com.kopylov.movieland.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {

    private int id;
    private UserDto user;
    private String text;
}
