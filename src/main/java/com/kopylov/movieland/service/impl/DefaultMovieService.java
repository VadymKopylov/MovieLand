package com.kopylov.movieland.service.impl;

import com.kopylov.movieland.dto.MovieDto;
import com.kopylov.movieland.dto.ReviewDto;
import com.kopylov.movieland.dto.UserDto;
import com.kopylov.movieland.entity.Movie;
import com.kopylov.movieland.exception.NotFoundException;
import com.kopylov.movieland.repository.MovieRepository;
import com.kopylov.movieland.service.MovieService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DefaultMovieService implements MovieService {

    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<Movie> getAll(Optional<String> rating, Optional<String> price) {
        List<Movie> allMovies = movieRepository.findAll();
        return sortedByCriteria(allMovies, rating, price);
    }

    @Override
    public List<Movie> getRandomMovie() {
        return movieRepository.findRandomMovies(3);
    }

    @Override
    public List<Movie> getByGenre(Long genreId, Optional<String> rating, Optional<String> price) {
        List<Movie> moviesByGenre = movieRepository.findByGenresId(genreId);
        return sortedByCriteria(moviesByGenre, rating, price);
    }

    @Override
    public MovieDto getById(long movieId) {
        Optional<Movie> movie = movieRepository.findById(movieId);
        if (movie.isEmpty()) {
            throw new NotFoundException("Not found movie by id" + movieId);
        }
        MovieDto movieDto = modelMapper.map(movie, MovieDto.class);

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

    private List<Movie> sortedByCriteria(List<Movie> movies, Optional<String> rating, Optional<String> price) {
        Comparator<Movie> comparator = getMovieComparator(rating, price);
        return movies.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    private Comparator<Movie> getMovieComparator(Optional<String> rating, Optional<String> price) {
        Comparator<Movie> comparator = Comparator.comparing(Movie::getId);

        if (rating.isPresent()) {
            comparator = rating.get().equalsIgnoreCase("asc") ? Comparator.comparing(Movie::getRating)
                    : Comparator.comparing(Movie::getRating).reversed();
        } else if (price.isPresent()) {
            comparator = price.get().equalsIgnoreCase("asc") ? Comparator.comparing(Movie::getPrice)
                    : Comparator.comparing(Movie::getPrice).reversed();
        }

        return comparator;
    }
}
