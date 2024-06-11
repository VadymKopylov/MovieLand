package com.kopylov.movieland.repository;

import com.kopylov.movieland.entity.Movie;
import com.kopylov.movieland.entity.SortOrder;

import java.util.List;

public interface MovieRepositoryCustom {

    List<Movie> findAllSorted(SortOrder rating, SortOrder price);

    List<Movie> findByGenreIdSorted(int genreId, SortOrder ratingSortOrder, SortOrder priceSortOrder);
}