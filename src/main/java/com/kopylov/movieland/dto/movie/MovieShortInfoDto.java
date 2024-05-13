package com.kopylov.movieland.dto.movie;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieShortInfoDto {

    private int id;
    private String nameRussian;
    private String nameNative;
    private String yearOfRelease;
    private String description;
    private double rating;
    private double price;
    private String picturePath;

}