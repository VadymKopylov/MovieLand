package com.kopylov.movieland.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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

    @ManyToMany(mappedBy = "genres")
    @JsonIgnore
    private List<Movie> movies;
}
