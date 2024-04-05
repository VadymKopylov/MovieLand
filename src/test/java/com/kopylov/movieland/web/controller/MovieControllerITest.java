package com.kopylov.movieland.web.controller;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.kopylov.movieland.AbstractBaseITest;
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
class MovieControllerITest extends AbstractBaseITest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DataSet(value = "datasets/movies_dataset.yml",
            cleanAfter = true, cleanBefore = true, skipCleaningFor = "flyway_schema_history")
    @ExpectedDataSet(value = "datasets/movies_dataset.yml")
    public void testGetMovies_ReturnCorrectData() throws Exception {
        mockMvc.perform(get("/api/v1/movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nameRussian").value("Побег из Шоушенка"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nameRussian").value("Зеленая миля"))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].nameRussian").value("Форрест Гамп"))
                .andExpect(jsonPath("$[3].id").value(4))
                .andExpect(jsonPath("$[3].nameRussian").value("Побег из Алькатраза"))
                .andExpect(jsonPath("$[4].id").value(5))
                .andExpect(jsonPath("$[4].nameRussian").value("Назад в будущее"));
    }

    @Test
    @DataSet(value = "datasets/movies_dataset.yml",
            cleanAfter = true, cleanBefore = true, skipCleaningFor = "flyway_schema_history")
    public void testGetRandomMovies_ReturnAllFieldsInJson() throws Exception {
        mockMvc.perform(get("/api/v1/movies/random")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").exists())
                .andExpect(jsonPath("$[*].nameRussian").exists())
                .andExpect(jsonPath("$[*].nameNative").exists())
                .andExpect(jsonPath("$[*].yearOfRelease").exists())
                .andExpect(jsonPath("$[*].rating").exists())
                .andExpect(jsonPath("$[*].price").exists())
                .andExpect(jsonPath("$[*].picturePath").exists());
    }

    @Test
    @DataSet(value = "datasets/movies_dataset.yml",
            cleanAfter = true, cleanBefore = true, skipCleaningFor = "flyway_schema_history")
    public void testGetRandomMovies_ReturnUniqueMovies() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/movies/random")
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
    @DataSet(value = "datasets/movies_by_genre_dataset.yml",
            cleanAfter = true, cleanBefore = true, skipCleaningFor = "flyway_schema_history")
    public void testGetMoviesByGenre_ReturnCorrectData() throws Exception {
        mockMvc.perform(get("/api/v1/movies/genre/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(5))
                .andExpect(jsonPath("$[0].nameRussian").value("Назад в будущее"))
                .andExpect(jsonPath("$[0].nameNative").value("Back to the Future"))
                .andExpect(jsonPath("$[0].yearOfRelease").value("1985"))
                .andExpect(jsonPath("$[0].rating").value(8.5))
                .andExpect(jsonPath("$[0].price").value(110.0))
                .andExpect(jsonPath("$[0].picturePath").value("https://images-na.ssl-images-amazon.com/images/I/81c%2BM2w8amL._AC_SY679_.jpg"))
                .andExpect(jsonPath("$[1].id").value(7))
                .andExpect(jsonPath("$[1].nameRussian").value("Звёздные войны: Эпизод 4 – Новая надежда"))
                .andExpect(jsonPath("$[1].nameNative").value("Star Wars: Episode IV - A New Hope"))
                .andExpect(jsonPath("$[1].yearOfRelease").value("1977"))
                .andExpect(jsonPath("$[1].rating").value(8.6))
                .andExpect(jsonPath("$[1].price").value(145.55))
                .andExpect(jsonPath("$[1].picturePath").value("https://images-na.ssl-images-amazon.com/images/I/81er5FoX-tL._AC_SY679_.jpg"));
    }

    @Test
    @DataSet(value = "datasets/movies_dataset.yml",
            cleanAfter = true, cleanBefore = true, skipCleaningFor = "flyway_schema_history")
    public void testGetAllMovies_WithSortingByRatingDesc() throws Exception {
        mockMvc.perform(get("/api/v1/movies")
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
    @DataSet(value = "datasets/movies_dataset.yml",
            cleanAfter = true, cleanBefore = true, skipCleaningFor = "flyway_schema_history")
    public void testGetAllMovies_WithSortingByRatingAsc() throws Exception {
        mockMvc.perform(get("/api/v1/movies")
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
    @DataSet(value = "datasets/movies_dataset.yml",
            cleanAfter = true, cleanBefore = true, skipCleaningFor = "flyway_schema_history")
    public void testGetAllMovies_WithSortingByPriceDesc() throws Exception {
        mockMvc.perform(get("/api/v1/movies")
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
    @DataSet(value = "datasets/movies_dataset.yml",
            cleanAfter = true, cleanBefore = true, skipCleaningFor = "flyway_schema_history")
    public void testGetAllMovies_WithSortingByPriceAsc() throws Exception {
        mockMvc.perform(get("/api/v1/movies")
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
    @DataSet(value = "datasets/movies_by_genre_dataset.yml",
            cleanAfter = true, cleanBefore = true, skipCleaningFor = "flyway_schema_history")
    public void testGetMoviesByGenre_WithSortingByRatingDesc() throws Exception {
        mockMvc.perform(get("/api/v1/movies/genre/1")
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
    @DataSet(value = "datasets/movies_by_genre_dataset.yml",
            cleanAfter = true, cleanBefore = true, skipCleaningFor = "flyway_schema_history")
    public void testGetMoviesByGenre_WithSortingByRatingAsc() throws Exception {
        mockMvc.perform(get("/api/v1/movies/genre/1")
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
    @DataSet(value = "datasets/movies_by_genre_dataset.yml",
            cleanAfter = true, cleanBefore = true, skipCleaningFor = "flyway_schema_history")
    public void testGetMoviesByGenre_WithSortingByPriceDesc() throws Exception {
        mockMvc.perform(get("/api/v1/movies/genre/1")
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
    @DataSet(value = "datasets/movies_by_genre_dataset.yml",
            cleanAfter = true, cleanBefore = true, skipCleaningFor = "flyway_schema_history")
    public void testGetMoviesByGenre_WithSortingByPriceAsc() throws Exception {
        mockMvc.perform(get("/api/v1/movies/genre/1")
                        .param("price", "asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(4))
                .andExpect(jsonPath("$[1].id").value(3))
                .andExpect(jsonPath("$[2].id").value(1))
                .andExpect(jsonPath("$[3].id").value(2))
                .andExpect(jsonPath("$[4].id").value(6));
    }

    @Test
    @DataSet(value = "datasets/movie_by_id_dataset.yml",
            cleanAfter = true, cleanBefore = true)
    public void testGetByIdMovie_ReturnCorrectJson() throws Exception {
        mockMvc.perform(get("/api/v1/movie/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nameRussian").value("Побег из Шоушенка"))
                .andExpect(jsonPath("$.nameNative").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$.yearOfRelease").value("1994"))
                .andExpect(jsonPath("$.description").value("Успешный банкир Энди Дюфрейн обвинен в убийстве собственной жены и ее любовника."))
                .andExpect(jsonPath("$.rating").value(8.89))
                .andExpect(jsonPath("$.price").value(123.45))
                .andExpect(jsonPath("$.picturePath").value("https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg"))
                .andExpect(jsonPath("$.genres[0].id").value(1))
                .andExpect(jsonPath("$.genres[0].name").value("драма"))
                .andExpect(jsonPath("$.genres[1].id").value(2))
                .andExpect(jsonPath("$.genres[1].name").value("криминал"))
                .andExpect(jsonPath("$.countries[0].id").value(1))
                .andExpect(jsonPath("$.countries[0].name").value("США"))
                .andExpect(jsonPath("$.reviews[0].id").value(1))
                .andExpect(jsonPath("$.reviews[0].user.id").value(3))
                .andExpect(jsonPath("$.reviews[0].user.nickname").value("Дарлин Эдвардс"))
                .andExpect(jsonPath("$.reviews[0].text").value("Гениальное кино!"))
                .andExpect(jsonPath("$.reviews[1].id").value(2))
                .andExpect(jsonPath("$.reviews[1].user.id").value(4))
                .andExpect(jsonPath("$.reviews[1].user.nickname").value("Габриэль Джексон"))
                .andExpect(jsonPath("$.reviews[1].text").value("Очень хороший фильм!"));
    }
}