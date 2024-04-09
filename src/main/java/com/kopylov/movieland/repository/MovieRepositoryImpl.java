package com.kopylov.movieland.repository;

import com.kopylov.movieland.entity.Movie;
import com.kopylov.movieland.entity.SortOrder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MovieRepositoryImpl implements MovieRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Movie> findAllSorted(SortOrder rating, SortOrder price) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);
        Root<Movie> root = criteriaQuery.from(Movie.class);

        return getMovies(rating, price, criteriaBuilder, criteriaQuery, root);
    }

    @Override
    public List<Movie> findByGenreIdSorted(Long genreId, SortOrder ratingSortOrder, SortOrder priceSortOrder) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);
        Root<Movie> root = criteriaQuery.from(Movie.class);

        root.join("genres");

        criteriaQuery.where(criteriaBuilder.equal(root.join("genres").get("id"), genreId));

        return getMovies(ratingSortOrder, priceSortOrder, criteriaBuilder, criteriaQuery, root);
    }

    private List<Movie> getMovies(SortOrder rating, SortOrder price, CriteriaBuilder criteriaBuilder,
                                  CriteriaQuery<Movie> criteriaQuery, Root<Movie> root) {

        List<Order> orders = new ArrayList<>();

        if (rating.getValue().equalsIgnoreCase("asc")) {
            orders.add(criteriaBuilder.asc(root.get("rating")));
        } else if (rating.getValue().equalsIgnoreCase("desc")) {
            orders.add(criteriaBuilder.desc(root.get("rating")));
        }


        if (price.getValue().equalsIgnoreCase("asc")) {
            orders.add(criteriaBuilder.asc(root.get("price")));
        } else if (price.getValue().equalsIgnoreCase("desc")) {
            orders.add(criteriaBuilder.desc(root.get("price")));
        }

        criteriaQuery.orderBy(orders);

        TypedQuery<Movie> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}