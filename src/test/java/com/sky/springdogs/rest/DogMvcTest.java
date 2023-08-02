package com.sky.springdogs.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sky.springdogs.domain.Dog;
import com.sky.springdogs.repos.DogRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // loads the application context
@AutoConfigureMockMvc // create a MockMVC bean
public class DogMvcTest {

    @Autowired // tells spring to inject the MockMVC bean into this class
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    private static AtomicInteger createID = new AtomicInteger(1);

    @Test
    void testCreate() throws Exception{
        // METHOD: POST
        // PATH: /create
        // BODY: JSON
        // HEADERS: CONTENT-TYPE: application\json
        Dog dog = new Dog("Lucky", "Labrador", 7);
        String dogJSON = this.mapper.writeValueAsString(dog);

        System.out.println("Dog Object: " + dog);
        System.out.println("Dog as JSON: " + dogJSON);

        RequestBuilder req = post("/create")
                .content(dogJSON)
                .contentType(MediaType.APPLICATION_JSON);

        // Used to check that status code is 201
        ResultMatcher checkStatus = status().isCreated();

        Dog expectedDog = new Dog(createID.getAndIncrement(), "Lucky", "Labrador", 7 );
        String responseBody = this.mapper.writeValueAsString(expectedDog); // The expected response

        ResultMatcher checkBody = content().json(responseBody);

        this.mvc.perform(req).andExpect(checkStatus).andExpect(checkBody);
    }

    @Test
    void testCreate2() throws Exception {
        String dogJSON = this.mapper.writeValueAsString(new Dog("Brian", "Yorkshire Terrier", 12));
        String responseBodyJSON = this.mapper.writeValueAsString(new Dog(createID.getAndIncrement(), "Brian", "Yorkshire Terrier", 12));

        RequestBuilder request = post("/create").content(dogJSON).contentType(MediaType.APPLICATION_JSON);

        ResultMatcher checkStatus = status().isCreated();
        ResultMatcher checkBody = content().json(responseBodyJSON);

        this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
    }

}
