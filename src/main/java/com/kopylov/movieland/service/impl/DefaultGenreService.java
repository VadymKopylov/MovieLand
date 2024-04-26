package com.kopylov.movieland.service.impl;

import com.kopylov.movieland.dto.GenreDto;
import com.kopylov.movieland.entity.Genre;
import com.kopylov.movieland.service.Cache;
import com.kopylov.movieland.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DefaultGenreService implements GenreService {

    private final Cache genreCache;

    @Override
    public List<GenreDto> getAll() {
        return genreCache.getAll();
    }
}
