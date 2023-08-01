package com.sky.springdogs.services;

import com.sky.springdogs.domain.Dog;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public interface DogService {
    Dog create(@RequestBody Dog dog);

    List<Dog> create(@RequestBody List<Dog> newDogs);

    public List<Dog> getAll();

    public Dog get(@PathVariable Integer id);

    public Dog update(
            @PathVariable Integer id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "breed", required = false) String breed,
            @RequestParam(value = "age", required = false) Integer age );

    public Dog remove (@PathVariable Integer id);
}
