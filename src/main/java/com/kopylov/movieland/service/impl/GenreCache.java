package com.kopylov.movieland.service.impl;

import com.kopylov.movieland.dto.GenreDto;
import com.kopylov.movieland.mapper.GenreMapper;
import com.kopylov.movieland.repository.GenreRepository;
import com.kopylov.movieland.service.Cache;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
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
        log.info("Invalidating genre cache");
        genresCache = genreMapper.toDtoList(genreRepository.findAll());
        log.info("Genre cache updated with {} entries", genresCache.size());
    }
}