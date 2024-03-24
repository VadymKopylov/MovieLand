package com.kopylov.movieland.service.impl;

import com.kopylov.movieland.entity.Genre;
import com.kopylov.movieland.repository.GenreRepository;
import com.kopylov.movieland.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class DefaultGenreService implements GenreService {

    private final GenreRepository genreRepository;
    private List<Genre> genreCache;

    @Autowired
    public DefaultGenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
        this.genreCache = new ArrayList<>();
    }

    @Override
    public List<Genre> getAll() {
        if (genreCache.isEmpty()) {
            updateCache();
        }
        return new ArrayList<>(genreCache);
    }

    @Scheduled(fixedRate = 4, timeUnit = TimeUnit.HOURS)
    void scheduledCacheUpdate() {
        updateCache();
    }

    private void updateCache() {
        genreCache = genreRepository.findAll();
    }
}
