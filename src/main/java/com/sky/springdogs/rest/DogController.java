package com.sky.springdogs.rest;

import com.sky.springdogs.domain.Dog;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DogController {

    private List<Dog> dogs = new ArrayList<>();

    @GetMapping("/hello")
    public String hello(){return "Hello World";}

    @PostMapping("/create")
    public HttpEntity<Dog> create(@RequestBody Dog dog){
        System.out.println("RECIEVED: " + dog);
        this.dogs.add(dog);

        Dog created = this.dogs.get(this.dogs.size()-1);

        // Return the last dog in dogs.
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping("/createMultiple")
    public HttpEntity<List<Dog>> create(@RequestBody List<Dog> newDogs){
        System.out.println("RECIEVED: "+ newDogs);
        this.dogs.addAll(newDogs);

        int toIndex = this.dogs.size();
        int fromIndex = toIndex - newDogs.size();
        List<Dog> created = this.dogs.subList(fromIndex, toIndex);

        return  new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public List<Dog> getAll(){return List.copyOf(this.dogs);}

    @GetMapping("/get/{id}")
    public Dog get(@PathVariable Integer id){
        System.out.println("RETRIEVING ID: " + id);
        // if (id < 0 || id >= this.dogs.size()) return null;

        return this.dogs.get(id);
    }

    @PatchMapping("update/{id}")
    public Dog update(
            @PathVariable Integer id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "breed", required = false) String breed,
            @RequestParam(value = "age", required = false) Integer age ){
        // if (id < 0 || id >= this.dogs.size()) return null;
        Dog upDog = this.dogs.get(id);

        if (name != null) upDog.setName(name);
        if (breed != null) upDog.setBreed(breed);
        if (age != null) upDog.setAge(age);

        return this.dogs.get(id);
    }

    @DeleteMapping("/delete/{id}")
    public Dog remove (@PathVariable Integer id) {
        Dog delDog = this.dogs.get(id);
        this.dogs.remove((int) id);

        return delDog;
    }
}
