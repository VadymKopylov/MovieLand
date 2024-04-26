package com.kopylov.movieland.service.impl;

import com.kopylov.movieland.dto.GenreDto;
import com.kopylov.movieland.mapper.GenreMapper;
import com.kopylov.movieland.repository.GenreRepository;
import com.kopylov.movieland.service.Cache;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class GenreCache implements Cache {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;
    private volatile List<GenreDto> genresCache;

    public List<GenreDto> getAll() {
        return new ArrayList<>(genresCache);
    }

    @PostConstruct
    @Scheduled(initialDelayString = "${scheduled.initialDelay.hours}",
            fixedDelayString = "${scheduled.fixedDelay.hours}", timeUnit = TimeUnit.HOURS)
    private void updateCache() {
        genresCache = genreMapper.toDtoList(genreRepository.findAll());
    }
}