package com.kopylov.movieland.repository;

import com.kopylov.movieland.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, Integer> {

    @Query("SELECT c FROM Country c JOIN c.movies m WHERE m.id = :movieId")
    List<Country> findByMovieId(int movieId);
}
