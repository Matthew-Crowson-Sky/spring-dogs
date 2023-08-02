package com.sky.springdogs.services;

import com.sky.springdogs.domain.Dog;
import com.sky.springdogs.exceptions.DogNotFoundException;
import com.sky.springdogs.repos.DogRepo;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Primary
@Service
public class DogServiceDB implements DogService {

    private DogRepo repo;

    public DogServiceDB(DogRepo repo) {
        this.repo = repo;
    }

    @Override
    public Dog create(Dog dog) { return this.repo.save(dog); }

    @Override
    public List<Dog> create(List<Dog> newDogs) {
        return this.repo.saveAll(newDogs);
    }

    @Override
    public List<Dog> getAll() {
        return this.repo.findAll();
    }

    @Override
    public Dog get(Integer id) {
        return this.repo.findById(id).orElseThrow(DogNotFoundException::new);
    }
    @Override
    public Dog update(Integer id, String name, String breed, Integer age) {
        Dog upDog = this.get(id);

        if (name != null) upDog.setName(name);
        if (breed != null) upDog.setBreed(breed);
        if (age != null) upDog.setAge(age);

        return this.repo.save(upDog);
    }

    @Override
    public Dog remove(Integer id) {
        Dog toDelete = this.get(id);
        this.repo.deleteById(id);
        return toDelete;
    }

    @Override
    public List<Dog> findByName(String name) {
        return this.repo.findByNameContainsIgnoreCase(name);
    }

    @Override
    public List<Integer> findAgeByName(String name) {
        return this.repo.findAgeByName(name);
    }
}
