package com.kopylov.movieland.service;

import com.kopylov.movieland.dto.ReviewDto;
import com.kopylov.movieland.dto.ReviewToSaveDto;
import com.kopylov.movieland.entity.Movie;

import java.util.List;

public interface ReviewService {

    void addReview(String token, Movie movie, ReviewToSaveDto reviewToSaveDto);

    List<ReviewDto> getReviewsByMovieId(int movieId);
}
