package com.kopylov.movieland.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "countries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Country {

    @Id
    private int id;

    private String name;

    @ManyToMany(mappedBy = "countries", cascade = CascadeType.ALL)
    private List<Movie> movies;
}