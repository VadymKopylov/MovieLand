package com.kopylov.movieland.dto;

import com.kopylov.movieland.entity.Country;
import com.kopylov.movieland.entity.Genre;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieDto {

    private int id;
    private String nameRussian;
    private String nameNative;
    private String yearOfRelease;
    private String description;
    private double rating;
    private double price;
    private String picturePath;
    private List<Country> countries;
    private List<Genre> genres;
    private List<ReviewDto> reviews;
}