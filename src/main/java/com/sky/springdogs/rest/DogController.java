package com.sky.springdogs.rest;

import com.sky.springdogs.domain.Dog;
import com.sky.springdogs.services.DogService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DogController {

    private DogService service;

    public DogController(DogService dogService){
        this.service = dogService;
    }

    @GetMapping("/hello")
    public String hello(){return "Hello World";}

    @PostMapping("/create")
    public HttpEntity<Dog> create(@RequestBody @Validated Dog dog){
        return new ResponseEntity<Dog>(this.service.create(dog),HttpStatus.CREATED);
    }

    @PostMapping("/createMultiple")
    public HttpEntity<List<Dog>> create(@RequestBody @Validated List<Dog> newDogs){
        return  new ResponseEntity<>(this.service.create(newDogs), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public List<Dog> getAll(){return this.service.getAll();}

    @GetMapping("/get/{id}")
    public Dog get(@PathVariable @Validated Integer id){
        return this.service.get(id);
    }

    @PatchMapping("update/{id}")
    public Dog update(
            @PathVariable @Validated Integer id,
            @RequestParam(value = "name", required = false) @Validated String name,
            @RequestParam(value = "breed", required = false) @Validated String breed,
            @RequestParam(value = "age", required = false) @Validated Integer age ){
        return this.service.update(id, name, breed, age);
    }

    @DeleteMapping("/delete/{id}")
    public Dog remove (@PathVariable @Validated Integer id) {
        return this.service.remove(id);
    }

    @GetMapping("findByName/{name}")
    public List<Dog> findByName (@PathVariable @Validated String name){
        return this.service.findByName(name);
    }

    @GetMapping("findAgeByName/{name}")
    public List<Integer> findAgeByName (@PathVariable @Validated String name){
        return this.service.findAgeByName(name);
    }
}
