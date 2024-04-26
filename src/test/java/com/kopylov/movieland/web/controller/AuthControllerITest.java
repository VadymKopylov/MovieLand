package com.kopylov.movieland.web.controller;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.kopylov.movieland.AbstractBaseITest;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@DBRider
class AuthControllerITest extends AbstractBaseITest {

    @Autowired
    private MockMvc mockMvc;

    private String token;

    @BeforeEach
    void setUp() throws Exception {

        JSONObject requestJson = new JSONObject();
        requestJson.put("username", "user");
        requestJson.put("email", "user@gmail.com");
        requestJson.put("password", "password");


        ResultActions resultActions = this.mockMvc.perform(postJson("/api/v1/auth/signup", requestJson.toString()));
        MvcResult mvcResult = resultActions.andDo(print()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(contentAsString);
        this.token = jsonObject.getString("token");
    }

    @Test
    @DataSet(value = "datasets/login_user_dataset.yml",
            cleanAfter = true, cleanBefore = true, skipCleaningFor = "flyway_schema_history")
    public void testLogin() throws Exception {

        JSONObject requestJson = new JSONObject();
        requestJson.put("email", "user@gmail.com");
        requestJson.put("password", "password");

        mockMvc.perform(postJson("/api/v1/auth/login", requestJson.toString())
                        .param("Authorization", "Bearer " + this.token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isString())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    private static MockHttpServletRequestBuilder postJson(String url, String content) {
        return post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
    }
}