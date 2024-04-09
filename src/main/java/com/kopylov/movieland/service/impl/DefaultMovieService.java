package com.kopylov.movieland.service.impl;

import com.kopylov.movieland.dto.MovieDto;
import com.kopylov.movieland.dto.ReviewDto;
import com.kopylov.movieland.dto.UserDto;
import com.kopylov.movieland.entity.CurrencyType;
import com.kopylov.movieland.entity.Movie;
import com.kopylov.movieland.entity.SortOrder;
import com.kopylov.movieland.exception.NotFoundException;
import com.kopylov.movieland.repository.MovieRepository;
import com.kopylov.movieland.service.CurrencyService;
import com.kopylov.movieland.service.MovieService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DefaultMovieService implements MovieService {

    private final MovieRepository movieRepository;
    private final CurrencyService currencyService;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<Movie> findAll(SortOrder ratingSortOrder, SortOrder priceSortOrder) {
        return movieRepository.findAllSorted(ratingSortOrder, priceSortOrder);
    }

    @Override
    public List<Movie> findRandom() {
        return movieRepository.findRandomMovies(3);
    }

    @Override
    public List<Movie> findByGenre(Long genreId, SortOrder ratingSortOrder, SortOrder priceSortOrder) {
        return movieRepository.findByGenreIdSorted(genreId, ratingSortOrder, priceSortOrder);
    }

    @Override
    public MovieDto findById(long movieId, CurrencyType currencyType) {
        Optional<Movie> movie = movieRepository.findById(movieId);
        if (movie.isEmpty()) {
            throw new NotFoundException("Not found movie by id " + movieId);
        }
        MovieDto movieDto = modelMapper.map(movie, MovieDto.class);

        if (currencyType != null) {
            double convertedPrice = currencyService.convertPrice(movieDto.getPrice(), currencyType);
            movieDto.setPrice(convertedPrice);
        }

        List<ReviewDto> reviewDtos = movie.get().getReviews().stream()
                .map(review -> {
                    ReviewDto reviewDto = modelMapper.map(review, ReviewDto.class);
                    UserDto userDto = modelMapper.map(review.getUser(), UserDto.class);
                    reviewDto.setUser(userDto);
                    return reviewDto;
                })
                .collect(Collectors.toList());
        movieDto.setReviews(reviewDtos);
        return movieDto;
    }
}
