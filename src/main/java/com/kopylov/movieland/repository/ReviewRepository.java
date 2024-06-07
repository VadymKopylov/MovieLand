package com.kopylov.movieland.repository;

import com.kopylov.movieland.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("select r from Review r where r.movie.id = :movieId")
    List<Review> findAllByMovieId(int movieId);
}
