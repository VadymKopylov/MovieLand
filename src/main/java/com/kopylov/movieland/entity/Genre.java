package com.kopylov.movieland.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "genres")
@AllArgsConstructor
@NoArgsConstructor
public class Genre {

    @Id
    private Long id;

    @Column(unique = true)
    private String name;
}
