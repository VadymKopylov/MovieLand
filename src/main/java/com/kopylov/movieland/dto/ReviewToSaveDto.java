package com.kopylov.movieland.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReviewToSaveDto {

    private int movieId;
    private String text;
}
