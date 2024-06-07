package com.kopylov.movieland.service;

import com.kopylov.movieland.dto.movie.MovieFullInfoDto;

public interface MovieEnrichmentService {

    void enrich(MovieFullInfoDto movieDto, EnrichmentType... types);

    enum EnrichmentType {
        COUNTRIES,
        GENRES,
        REVIEWS
    }
}