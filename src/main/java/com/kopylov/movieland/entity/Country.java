package com.kopylov.movieland.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "countries")
public class Country {

    @Id
    private int id;

    private String name;

    @ManyToMany(mappedBy = "countries")
    @JsonIgnore
    private List<Movie> movies;
}