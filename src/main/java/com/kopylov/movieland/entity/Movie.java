package com.kopylov.movieland.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany
    @JoinColumn(
            name = "movie_id",
            referencedColumnName = "id"
    )
    private List<Review> reviews;
}
