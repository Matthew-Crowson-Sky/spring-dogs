package com.sky.springdogs.services;

import com.sky.springdogs.domain.Dog;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DogServiceList implements DogService{

    private List<Dog> dogs = new ArrayList<>();

    @Override
    public Dog create(Dog dog) {
        System.out.println("RECIEVED: " + dog);
        this.dogs.add(dog);

        return this.dogs.get(this.dogs.size()-1);
    }

    @Override
    public List<Dog> create(List<Dog> newDogs) {
        System.out.println("RECIEVED: "+ newDogs);
        this.dogs.addAll(newDogs);

        int toIndex = this.dogs.size();
        int fromIndex = toIndex - newDogs.size();

        return this.dogs.subList(fromIndex, toIndex);
    }

    @Override
    public List<Dog> getAll() {
        return List.copyOf(this.dogs);
    }

    @Override
    public Dog get(Integer id) {
        // if (id < 0 || id >= this.dogs.size()) return null;
        return this.dogs.get(id);
    }

    @Override
    public Dog update(Integer id, String name, String breed, Integer age) {
        // if (id < 0 || id >= this.dogs.size()) return null;
        Dog upDog = this.dogs.get(id);

        if (name != null) upDog.setName(name);
        if (breed != null) upDog.setBreed(breed);
        if (age != null) upDog.setAge(age);

        return this.dogs.get(id);
    }

    @Override
    public Dog remove(Integer id) {
        Dog delDog = this.dogs.get(id);
        this.dogs.remove((int) id);

        return delDog;
    }
}
