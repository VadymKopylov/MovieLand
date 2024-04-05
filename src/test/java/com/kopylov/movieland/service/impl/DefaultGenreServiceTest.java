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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DefaultGenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @Spy
    private List<Genre> genreCache = new ArrayList<>();

    @InjectMocks
    private GenreCache cache;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCacheReturnDataFromCache() {
        Genre genre1 = new Genre(1, "Драма");
        Genre genre2 = new Genre(2, "Фантастика");
        genreCache.add(genre1);
        genreCache.add(genre2);

        List<Genre> genres = cache.getAll();

        assertEquals(2, genres.size());
        assertEquals("Драма", genres.get(0).getName());
        assertEquals("Фантастика", genres.get(1).getName());
        verify(genreRepository, never()).findAll();
    }

    @Test
    void testGetAllCacheEmptyCacheFetchesFromRepository() {
        Genre genre1 = new Genre(1, "Драма");
        Genre genre2 = new Genre(2, "Фантастика");
        List<Genre> genresRepo = new ArrayList<>();
        genresRepo.add(genre1);
        genresRepo.add(genre2);

        when(genreRepository.findAll()).thenReturn(genresRepo);

        List<Genre> genres = cache.getAll();

        assertEquals(2, genres.size());
        verify(genreRepository, times(1)).findAll();
    }

    @Test
    void testGetAllReturnsUnmodifiableList() {
        assertThrows(UnsupportedOperationException.class, () -> cache.getAll().add(new Genre(1, "Test")));
    }
}
