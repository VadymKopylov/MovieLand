package com.kopylov.movieland.service.impl;

import com.kopylov.movieland.dto.GenreDto;
import com.kopylov.movieland.mapper.GenreMapper;
import com.kopylov.movieland.repository.GenreRepository;
import com.kopylov.movieland.service.Cache;
import com.kopylov.movieland.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DefaultGenreService implements GenreService {

    private final Cache genreCache;
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Override
    public List<GenreDto> getAll() {
        return genreCache.getAll();
    }

    @Override
    public List<GenreDto> getGenresByMovieId(int movieId) {
        return genreMapper.toDtoList(genreRepository.findAllByMovieId(movieId));
    }

    @Override
    public List<GenreDto> getAllGenresByIds(List<Integer> ids) {
        return genreMapper.toDtoList(genreRepository.findAllById(ids));
    }
}
