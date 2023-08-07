package com.sky.springdogs.services;

import com.sky.springdogs.domain.Dog;
import com.sky.springdogs.repos.DogRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest // boots the context (loads all the beans)
public class DogServiceDBUnitTest {

    @Autowired
    private DogServiceDB service;

    @MockBean // instructs spring to create a mock version of this class
    private DogRepo repo;

    @Test
    void testCreate(){
        Dog toCreate = new Dog("Fluffy", "Husky", 12);
        Dog savedDog = new Dog(3, "Fluffy", "Husky", 12);

        Mockito.when(this.repo.save(toCreate)).thenReturn(savedDog);

        Assertions.assertEquals(savedDog, this.service.create(toCreate));
    }

    @Test
    void testGetAll(){
        Dog dog1 = new Dog(1, "Dave", "Golden Retriever", 3);
        Dog dog2 = new Dog(2, "Lucy", "Alsatian", 9);
        Dog dog3 = new Dog(3, "Buddy", "Labrador", 11);
        List<Dog> dogs = new ArrayList<>(List.of(dog1, dog2, dog3));

        Mockito.when(repo.findAll()).thenReturn(dogs);

        Assertions.assertEquals(dogs, this.service.getAll());
    }

    @Test
    void tesGet(){
        int id = 4;
        Dog expectedDog = new Dog(id, "Mario", "Poodle", 8);

        Mockito.when(repo.findById(id)).thenReturn(Optional.of(expectedDog));

        Assertions.assertEquals(expectedDog, service.get(id));
    }

    @Test
    void testUpdate(){
        int id = 1;
        Dog existingDog = new Dog(id, "Fluffy", "Husky", 12); // Mock existing dog

        // To update
        String name = "Willow";
        String breed = "Alaskan Malamute";
        int age = 11;
        Dog updatedDog = new Dog(id, name, breed, age);

        Mockito.when(repo.findById(id)).thenReturn(Optional.of(existingDog));
        Mockito.when(repo.save(updatedDog)).thenReturn(updatedDog);

        Assertions.assertEquals(updatedDog, this.service.update(id, name, breed, age));

        Mockito.verify(this.repo, Mockito.times(1)).findById(id);
        Mockito.verify(this.repo, Mockito.times(1)).save(updatedDog);
    }

    @Test
    void testRemove(){
        int id = 2;
        Dog toRemove = new Dog(id, "Marcus", "Labrador", 6);

        Mockito.when(this.repo.findById(id)).thenReturn(Optional.of(toRemove));

        Assertions.assertEquals(toRemove, this.service.remove(id));
    }

    @Test
    void testFindByName(){
        Dog dog1 = new Dog(1, "Lucky", "Labrador", 4);
        Dog dog2 = new Dog(2, "Lucky", "Poodle", 5);
        List<Dog> dogs = new ArrayList<>(List.of(dog1, dog2));

        Mockito.when(repo.findByNameContainsIgnoreCase("Lucky")).thenReturn(dogs);

        Assertions.assertEquals(dogs, service.findByName("Lucky"));
    }

    @Test
    void testFindAgeByName(){
        Integer age1 = 4;
        Integer age2 = 5;
        List<Integer> ages = new ArrayList<>(List.of(age1, age2));

        Mockito.when(repo.findAgeByName("Lucky")).thenReturn(ages);

        Assertions.assertEquals(ages, service.findAgeByName("Lucky"));
    }


}
