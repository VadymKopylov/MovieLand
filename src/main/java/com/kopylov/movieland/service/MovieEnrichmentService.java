package com.kopylov.movieland.service;

import com.kopylov.movieland.dto.movie.MovieEditDto;
import com.kopylov.movieland.dto.movie.MovieFullInfoDto;
import com.kopylov.movieland.entity.Movie;

public interface MovieEnrichmentService {

    void enrich(MovieFullInfoDto movieDto, EnrichmentType... types);

    void enrichByIds(Movie movie, MovieEditDto movieEditDto);

    enum EnrichmentType {
        COUNTRIES,
        GENRES,
        REVIEWS
    }
}