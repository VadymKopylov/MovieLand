package com.kopylov.movieland.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "movies")
public class Movie {

    @Id
    private Long id;
    private String nameRussian;
    private String nameNative;
    private String yearOfRelease;
    private double rating;
    private double price;
    private String picturePath;

    @ManyToMany
    @JoinTable(name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genre = new ArrayList<>();
}
