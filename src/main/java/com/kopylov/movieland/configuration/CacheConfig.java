package com.kopylov.movieland.configuration;

import com.kopylov.movieland.dto.movie.MovieFullInfoDto;
import com.kopylov.movieland.service.MovieService;
import com.kopylov.movieland.util.SoftReferenceCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.function.Function;

@Configuration
public class CacheConfig {

    private MovieService movieService;

    @Autowired
    public void setMovieService(@Lazy MovieService movieService) {
        this.movieService = movieService;
    }

    @Bean
    public SoftReferenceCache<Integer, MovieFullInfoDto> movieCache() {
        Function<Integer, MovieFullInfoDto> movieLoader = movieService::findInDb;

        return new SoftReferenceCache<>(movieLoader);
    }
}
