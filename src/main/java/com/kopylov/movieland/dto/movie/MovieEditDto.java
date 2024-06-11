package com.kopylov.movieland.dto.movie;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieEditDto {

    private int id;
    private String nameRussian;
    private String nameNative;
    private String yearOfRelease;
    private String description;
    private double rating;
    private double price;
    private String picturePath;
    private List<Integer> countries;
    private List<Integer> genres;
}
