package com.kopylov.movieland.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "genres")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Genre {

    @Id
    private Long id;

    @Column(unique = true)
    private String name;
}
