package com.sky.springdogs.services;

import com.sky.springdogs.domain.Dog;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
public class DogServiceDB implements DogService {
    @Override
    public Dog create(Dog dog) {
        return null;
    }

    @Override
    public List<Dog> create(List<Dog> newDogs) {
        return null;
    }

    @Override
    public List<Dog> getAll() {
        return null;
    }

    @Override
    public Dog get(Integer id) {
        return null;
    }

    @Override
    public Dog update(Integer id, String name, String breed, Integer age) {
        return null;
    }

    @Override
    public Dog remove(Integer id) {
        return null;
    }
}
