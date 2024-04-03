package com.kopylov.movieland.service;

import com.kopylov.movieland.entity.Genre;

import java.util.List;

public interface Cache {
    List<Genre> getAll();
}
