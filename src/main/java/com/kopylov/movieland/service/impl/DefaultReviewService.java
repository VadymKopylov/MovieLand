package com.kopylov.movieland.service.impl;

import com.kopylov.movieland.dto.ReviewDto;
import com.kopylov.movieland.dto.ReviewToSaveDto;
import com.kopylov.movieland.entity.Movie;
import com.kopylov.movieland.entity.Review;
import com.kopylov.movieland.entity.User;
import com.kopylov.movieland.mapper.ReviewMapper;
import com.kopylov.movieland.repository.ReviewRepository;
import com.kopylov.movieland.service.ReviewService;
import com.kopylov.movieland.service.UserService;
import com.kopylov.movieland.web.controller.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DefaultReviewService implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final JwtService jwtService;
    private final UserService userService;
    private final ReviewMapper reviewMapper;

    @Override
    public void addReview(String authHeader, Movie movie, ReviewToSaveDto reviewToSaveDto) {
        String token = jwtService.getTokenFromHeader(authHeader);
        User user = userService.findByEmail(jwtService.extractUserName(token));

        Review review = Review.builder()
                .movie(movie)
                .user(user)
                .text(reviewToSaveDto.getText())
                .build();

        reviewRepository.save(review);
    }

    @Override
    public List<ReviewDto> getReviewsByMovieId(int movieId) {
        return reviewMapper.toDtoList(reviewRepository.findAllByMovieId(movieId));
    }
}
