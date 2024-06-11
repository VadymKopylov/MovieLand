package com.kopylov.movieland.repository;

import com.kopylov.movieland.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer>, MovieRepositoryCustom {

    @Query(value = "SELECT * FROM movies ORDER BY RANDOM() LIMIT ?1", nativeQuery = true)
    List<Movie> findRandomMovies(int limit);
}
