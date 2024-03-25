package com.kopylov.movieland.web.controller;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.kopylov.movieland.AbstractBaseITest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DBRider
@AutoConfigureMockMvc
@Slf4j
class MovieControllerTest extends AbstractBaseITest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DataSet(value = "datasets/movies/movies_dataset.yml")
    @ExpectedDataSet(value = "datasets/movies/movies_dataset.yml")
    public void testGetMovies_ReturnCorrectJson() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DataSet(value = "datasets/movies/movies_dataset.yml")
    public void testGetRandomMovies_ReturnAllFieldsInJson() throws Exception {
        MvcResult result = mockMvc.perform(get("http://localhost:8080/api/v1/movies/random")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").exists())
                .andExpect(jsonPath("$[*].nameRussian").exists())
                .andExpect(jsonPath("$[*].nameNative").exists())
                .andExpect(jsonPath("$[*].yearOfRelease").exists())
                .andExpect(jsonPath("$[*].rating").exists())
                .andExpect(jsonPath("$[*].price").exists())
                .andExpect(jsonPath("$[*].picturePath").exists())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        log.info("Response JSON: {}", responseJson);
    }

    @Test
    @DataSet(value = "datasets/movies/movies_dataset.yml")
    public void testGetRandomMovies_ReturnUniqueMovies() throws Exception {
        MvcResult result = mockMvc.perform(get("http://localhost:8080/api/v1/movies/random")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();

        List<Map<String, Object>> movies = new ObjectMapper().readValue(responseJson,
                new TypeReference<List<Map<String, Object>>>() {
                });

        List<Integer> movieIds = movies.stream()
                .map(movie -> (Integer) movie.get("id"))
                .collect(Collectors.toList());

        assertThat(movieIds, hasSize(3));
        assertThat(movieIds, hasSize(equalTo(new HashSet<>(movieIds).size())));
    }

    @Test
    @DataSet(value = "datasets/movies/movies_dataset.yml")
    public void testGetMoviesByGenre() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/movies/genre/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").value(8))
                .andExpect(jsonPath("$[*].nameRussian").value("Криминальное чтиво"))
                .andExpect(jsonPath("$[*].nameNative").value("Pulp Fiction"))
                .andExpect(jsonPath("$[*].yearOfRelease").value("1994"))
                .andExpect(jsonPath("$[*].rating").value(8.9))
                .andExpect(jsonPath("$[*].price").value(155.0))
                .andExpect(jsonPath("$[*].picturePath").value("https://images-na.ssl-images-amazon.com/images/I/61v%2BGcL1-xL._AC_SY679_.jpg"))
                .andExpect(jsonPath("$[*].genre.id").value(2))
                .andExpect(jsonPath("$[*].genre.name").value("криминал"));
    }

    @Test
    @DataSet(value = "datasets/movies/movies_dataset.yml")
    public void testGetAllMoviesWithSortingByRatingDesc() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/movies")
                        .param("rating", "desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(8))
                .andExpect(jsonPath("$[1].id").value(1))
                .andExpect(jsonPath("$[2].id").value(2))
                .andExpect(jsonPath("$[3].id").value(3))
                .andExpect(jsonPath("$[4].id").value(7))
                .andExpect(jsonPath("$[5].id").value(5))
                .andExpect(jsonPath("$[6].id").value(6))
                .andExpect(jsonPath("$[7].id").value(4));
    }

    @Test
    @DataSet(value = "datasets/movies/movies_dataset.yml")
    public void testGetAllMoviesWithSortingByRatingAsc() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/movies")
                        .param("rating", "asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(4))
                .andExpect(jsonPath("$[1].id").value(6))
                .andExpect(jsonPath("$[2].id").value(5))
                .andExpect(jsonPath("$[3].id").value(7))
                .andExpect(jsonPath("$[4].id").value(3))
                .andExpect(jsonPath("$[5].id").value(2))
                .andExpect(jsonPath("$[6].id").value(1))
                .andExpect(jsonPath("$[7].id").value(8));
    }

    @Test
    @DataSet(value = "datasets/movies/movies_dataset.yml")
    public void testGetAllMoviesWithSortingByPriceDesc() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/movies")
                        .param("price", "desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(8))
                .andExpect(jsonPath("$[1].id").value(6))
                .andExpect(jsonPath("$[2].id").value(7))
                .andExpect(jsonPath("$[3].id").value(2))
                .andExpect(jsonPath("$[4].id").value(1))
                .andExpect(jsonPath("$[5].id").value(5))
                .andExpect(jsonPath("$[6].id").value(3))
                .andExpect(jsonPath("$[7].id").value(4));
    }

    @Test
    @DataSet(value = "datasets/movies/movies_dataset.yml")
    public void testGetAllMoviesWithSortingByPriceAsc() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/movies")
                        .param("price", "asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(4))
                .andExpect(jsonPath("$[1].id").value(3))
                .andExpect(jsonPath("$[2].id").value(5))
                .andExpect(jsonPath("$[3].id").value(1))
                .andExpect(jsonPath("$[4].id").value(2))
                .andExpect(jsonPath("$[5].id").value(7))
                .andExpect(jsonPath("$[6].id").value(6))
                .andExpect(jsonPath("$[7].id").value(8));
    }

    @Test
    @DataSet(value = "datasets/movies/movies_dataset.yml")
    public void testGetMoviesByGenreWithSortingByRatingDesc() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/movies/genre/1")
                        .param("rating", "desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[3].id").value(6))
                .andExpect(jsonPath("$[4].id").value(4));
    }

    @Test
    @DataSet(value = "datasets/movies/movies_dataset.yml")
    public void testGetMoviesByGenreWithSortingByRatingAsc() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/movies/genre/1")
                        .param("rating", "asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(4))
                .andExpect(jsonPath("$[1].id").value(6))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[3].id").value(2))
                .andExpect(jsonPath("$[4].id").value(1));
    }

    @Test
    @DataSet(value = "datasets/movies/movies_dataset.yml")
    public void testGetMoviesByGenreWithSortingByPriceDesc() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/movies/genre/1")
                        .param("price", "desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(6))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[2].id").value(1))
                .andExpect(jsonPath("$[3].id").value(3))
                .andExpect(jsonPath("$[4].id").value(4));
    }

    @Test
    @DataSet(value = "datasets/movies/movies_dataset.yml")
    public void testGetMoviesByGenreWithSortingByPriceAsc() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/movies/genre/1")
                        .param("price", "asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(4))
                .andExpect(jsonPath("$[1].id").value(3))
                .andExpect(jsonPath("$[2].id").value(1))
                .andExpect(jsonPath("$[3].id").value(2))
                .andExpect(jsonPath("$[4].id").value(6));
    }
}