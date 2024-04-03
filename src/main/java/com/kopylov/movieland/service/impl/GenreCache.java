package com.kopylov.movieland.service.impl;

import com.kopylov.movieland.entity.Genre;
import com.kopylov.movieland.repository.GenreRepository;
import com.kopylov.movieland.service.Cache;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
@AllArgsConstructor
public class GenreCache implements Cache {

    private final GenreRepository genreRepository;
    private List<Genre> genreCache;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public List<Genre> getAll() {
        lock.readLock().lock();
        if (genreCache.isEmpty()) {
            updateCache();
        }
        try {
            return Collections.unmodifiableList(genreCache);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Scheduled(fixedRate = 4, timeUnit = TimeUnit.HOURS)
    private void invalidate() {
        lock.readLock().lock();
        try {
            updateCache();
        } finally {
            lock.readLock().unlock();
        }
    }

    private void updateCache() {
        genreCache = genreRepository.findAll();
    }
}
