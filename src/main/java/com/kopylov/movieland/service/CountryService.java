package com.kopylov.movieland.service;

import com.kopylov.movieland.dto.CountryDto;

import java.util.List;

public interface CountryService {

    List<CountryDto> getCountriesByMovieId(int movieId);
}
