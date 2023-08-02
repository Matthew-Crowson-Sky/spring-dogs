package com.sky.springdogs.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sky.springdogs.domain.Dog;
import com.sky.springdogs.repos.DogRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // loads the application context
@AutoConfigureMockMvc // create a MockMVC bean
@Sql(scripts = {"classpath:dog-schema.sql", "classpath:dog-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class DogMvcTest {

    @Autowired // tells spring to inject the MockMVC bean into this class
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

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

        Dog expectedDog = new Dog(4, "Lucky", "Labrador", 7 );
        String responseBody = this.mapper.writeValueAsString(expectedDog); // The expected response

        ResultMatcher checkBody = content().json(responseBody);

        this.mvc.perform(req).andExpect(checkStatus).andExpect(checkBody);
    }

    @Test
    void testCreate2() throws Exception {
        String dogJSON = this.mapper.writeValueAsString(new Dog("Brian", "Yorkshire Terrier", 12));
        String responseBodyJSON = this.mapper.writeValueAsString(new Dog(4, "Brian", "Yorkshire Terrier", 12));

        RequestBuilder request = post("/create").content(dogJSON).contentType(MediaType.APPLICATION_JSON);

        ResultMatcher checkStatus = status().isCreated();
        ResultMatcher checkBody = content().json(responseBodyJSON);

        this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
    }

    @Test
    void testCreateMultiple() throws Exception{
        Dog dog1 = new Dog("Jess", "Golden Retriever", 8);
        Dog dog2 = new Dog("Buddy", "Labradoodle", 6);
        List<Dog> dogs = List.of(dog1, dog2);
        String requestBodyJSON = this.mapper.writeValueAsString(dogs);

        RequestBuilder request = post("/createMultiple").content(requestBodyJSON).contentType(MediaType.APPLICATION_JSON);

        Dog expectedDog1 = new Dog(4, "Jess", "Golden Retriever", 8);
        Dog expectedDog2 = new Dog(5, "Buddy", "Labradoodle", 6);
        List<Dog> expectedDogs = List.of(expectedDog1, expectedDog2);
        String responseBodyJSON = this.mapper.writeValueAsString(expectedDogs);

        ResultMatcher checkStatus = status().isCreated();
        ResultMatcher checkBody = content().json(responseBodyJSON);

        this.mvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
    }

    @Test
    void testRead() throws Exception {
        final int id = 2;
        String responseBody = this.mapper.writeValueAsString(new Dog(id, "Doug", "Pug", 7));
        this.mvc.perform(get("/get/" + id))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));
    }

    @Test
    void testReadAll() throws Exception {
        String responseBody = this.mapper.writeValueAsString(List.of(
                new Dog(1, "Clifford", "Golden Retriever", 12),
                new Dog(2, "Doug", "Pug", 7),
                new Dog(3, "Fenton", "Jack Russell", 2)
        ));
        this.mvc.perform(get("/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));
    }

    @Test
    void testFindByName() throws Exception {
        String responseBody = this.mapper.writeValueAsString(List.of(new Dog(2,"Doug", "Pug", 7)));

        this.mvc.perform(get("/findByName/Doug"))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));
    };

    @Test
    void testUpdate() throws Exception {
        final int id = 3;
        RequestBuilder req = patch("/update/"+id)
                .queryParam("name", "Sherb")
                .queryParam("breed", "Poodle")
                .queryParam("age", "5");

        String responseBody = this.mapper.writeValueAsString(new Dog(id, "Sherb", "Poodle", 5));

        this.mvc.perform(req).andExpect(status().isOk()).andExpect(content().json(responseBody));
    }

    @Test
    void testUpdateEmpty() throws Exception {
        final int id = 3;
        RequestBuilder req = patch("/update/"+id);

        String responseBody = this.mapper.writeValueAsString(new Dog(id, "Fenton", "Jack Russell", 2));

        this.mvc.perform(req).andExpect(status().isOk()).andExpect(content().json(responseBody));
    }

    @Test
    void testUpdatePartial() throws Exception {
        final int id = 3;
        RequestBuilder req = patch("/update/"+id)
                .queryParam("name", "Sherb")
                .queryParam("age", "5");

        String responseBody = this.mapper.writeValueAsString(new Dog(id, "Sherb", "Jack Russell", 5));

        this.mvc.perform(req).andExpect(status().isOk()).andExpect(content().json(responseBody));
    }



    @Test
    void testDelete() throws Exception{
        // Expect OK and return the deleted dog
        final int id = 2;

        this.mvc.perform(delete("/delete/"+id))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        this.mapper.writeValueAsString(new Dog(2, "Doug", "Pug", 7))
                ));
    }

    // Nice to have but not required:

    @Test // try and GET an object that doesn't exist
    void testNotFound() throws Exception{
        this.mvc.perform(get("/get/50")).andExpect(status().isNotFound());
    }

    @Test // try and create an object that doesn't pass validation and check that the error works
    void testFailCreate() throws Exception {

        RequestBuilder request = post("/create")
                .content(this.mapper.writeValueAsString(
                        new Dog("Rolo","Dachsund", -60))
                )
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request).andExpect(status().isBadRequest());
    };
}
