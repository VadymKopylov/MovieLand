package com.kopylov.movieland.dto.movie;

import com.kopylov.movieland.dto.CountryDto;
import com.kopylov.movieland.dto.GenreDto;
import com.kopylov.movieland.dto.ReviewDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieFullInfoDto {

    private byte[] weight = new byte[1024 * 1024 * 10];
    private int id;
    private String nameRussian;
    private String nameNative;
    private String yearOfRelease;
    private String description;
    private double rating;
    private double price;
    private String picturePath;
    private List<CountryDto> countries;
    private List<GenreDto> genres;
    private List<ReviewDto> reviews;
}