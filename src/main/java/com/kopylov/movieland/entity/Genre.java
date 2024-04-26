package com.kopylov.movieland.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "genres")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Genre {

    @Id
    private int id;

    private String name;

    @ManyToMany(mappedBy = "genres", cascade = CascadeType.ALL)
    private List<Movie> movies;
}
