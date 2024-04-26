package com.kopylov.movieland.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "movies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name_russian")
    private String nameRussian;

    @Column(name = "name_native")
    private String nameNative;

    @Column(name = "year_of_release")
    private String yearOfRelease;

    @Column(name = "description")
    private String description;

    @Column(name = "rating")
    private double rating;

    @Column(name = "price")
    private double price;

    @Column(name = "picture_path")
    private String picturePath;

    @ManyToMany
    @JoinTable(
            name = "movie_country",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    private List<Country> countries;

    @ManyToMany
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @OneToMany(mappedBy = "movie")
    private List<Review> reviews;
}
