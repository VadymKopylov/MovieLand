package com.kopylov.movieland.service.impl;

import com.kopylov.movieland.entity.Genre;
import com.kopylov.movieland.repository.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DefaultGenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @Spy
    private List<Genre> genreCache = new ArrayList<>();

    @InjectMocks
    private DefaultGenreService genreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCacheReturnDataFromCache() {
        Genre genre1 = new Genre(1L, "Драма");
        Genre genre2 = new Genre(2L, "Фантастика");
        genreCache.add(genre1);
        genreCache.add(genre2);

        List<Genre> genres = genreService.getAll();

        assertEquals(2, genres.size());
        assertEquals("Драма", genres.get(0).getName());
        assertEquals("Фантастика", genres.get(1).getName());
        verify(genreRepository, never()).findAll();
    }

    @Test
    void testScheduledCacheUpdate() {
        genreService.scheduledCacheUpdate();

        verify(genreRepository, times(1)).findAll();
    }
}
