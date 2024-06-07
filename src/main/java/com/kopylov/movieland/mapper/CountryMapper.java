package com.kopylov.movieland.mapper;

import com.kopylov.movieland.dto.CountryDto;
import com.kopylov.movieland.entity.Country;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    CountryDto toDto(Country country);

    List<CountryDto> toDtoList(List<Country> countries);
}
