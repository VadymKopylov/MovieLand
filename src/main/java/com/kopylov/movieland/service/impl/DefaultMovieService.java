package com.kopylov.movieland.service.impl;

import com.kopylov.movieland.entity.Movie;
import com.kopylov.movieland.repository.MovieRepository;
import com.kopylov.movieland.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DefaultMovieService implements MovieService {

    private final MovieRepository movieRepository;

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
        List<Movie> moviesByGenre = movieRepository.findByGenreId(genreId);
        return sortedByCriteria(moviesByGenre, rating, price);
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
