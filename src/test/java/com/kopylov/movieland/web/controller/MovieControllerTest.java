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
//                .andExpect(content().json("[{\"id\": 1,\"nameRussian\": \"Побег из Шоушенка\"," +
//                        "\"nameNative\":\"The Shawshank Redemption\",\"yearOfRelease\": \"1994\"," +
//                        "\"rating\": 8.89,\"price\": 123.45," +
//                        "\"picturePath\":\"https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg\"},{" +
//                        "\"id\": 2,\"nameRussian\": \"Зеленая миля\",\"nameNative\": \"The Green Mile\"," +
//                        "\"yearOfRelease\": \"1999\",\"rating\":8.88,\"price\": 134.67," +
//                        "\"picturePath\":\"https://images-na.ssl-images-amazon.com/images/M/MV5BMTUxMzQyNjA5MF5BMl5BanBnXkFtZTYwOTU2NTY3._V1._SY209_CR0,0,140,209_.jpg\"}]"));
    }

    @Test
    @DataSet(value = "datasets/movies/random_movies_dataset.yml")
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
    @DataSet(value = "datasets/movies/random_movies_dataset.yml")
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
}