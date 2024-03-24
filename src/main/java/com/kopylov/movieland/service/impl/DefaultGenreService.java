package com.kopylov.movieland.service.impl;

import com.kopylov.movieland.entity.Genre;
import com.kopylov.movieland.repository.GenreRepository;
import com.kopylov.movieland.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DefaultGenreService implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }
}
