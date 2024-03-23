package com.kopylov.movieland.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameRussian;
    private String nameNative;
    private String yearOfRelease;
    private double rating;
    private double price;
    private String picturePath;
}
