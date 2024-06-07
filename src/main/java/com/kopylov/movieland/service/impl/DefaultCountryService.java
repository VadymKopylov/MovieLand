package com.kopylov.movieland.service.impl;

import com.kopylov.movieland.dto.CountryDto;
import com.kopylov.movieland.mapper.CountryMapper;
import com.kopylov.movieland.repository.CountryRepository;
import com.kopylov.movieland.service.CountryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DefaultCountryService implements CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    @Override
    public List<CountryDto> getCountriesByMovieId(int movieId) {
        return countryMapper.toDtoList(countryRepository.findByMovieId(movieId));
    }
}
