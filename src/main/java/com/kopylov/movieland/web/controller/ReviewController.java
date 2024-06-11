package com.kopylov.movieland.web.controller;

import com.kopylov.movieland.dto.ReviewToSaveDto;
import com.kopylov.movieland.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(path = "api/v1/review")
@RequiredArgsConstructor
public class ReviewController {

    private final MovieService movieService;

    @PostMapping()
    public void addReview(@RequestBody ReviewToSaveDto review,
                          @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        log.info("Adding review");
        movieService.addReview(review, authHeader);
    }
}
