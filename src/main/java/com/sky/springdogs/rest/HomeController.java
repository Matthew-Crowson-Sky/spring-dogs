package com.sky.springdogs.rest;


import com.sky.springdogs.domain.Home;
import com.sky.springdogs.services.HomeService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("homes/")
public class HomeController {
    
    private HomeService service;

    public HomeController(HomeService homeService){
        this.service = homeService;
    }

    @GetMapping("/hello")
    public String hello(){return "Hello World";}

    @PostMapping("/create")
    public HttpEntity<Home> create(@RequestBody @Validated Home home){
        return new ResponseEntity<Home>(this.service.create(home), HttpStatus.CREATED);
    }

    @PostMapping("/createMultiple")
    public HttpEntity<List<Home>> create(@RequestBody @Validated List<Home> newHomes){
        return  new ResponseEntity<>(this.service.create(newHomes), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public List<Home> getAll(){return this.service.getAll();}

    @GetMapping("/get/{id}")
    public Home get(@PathVariable @Validated Integer id){
        return this.service.get(id);
    }

    @PatchMapping("update/{id}")
    public Home update(
            @PathVariable @Validated Integer id,
            @RequestParam(value = "address", required = false) @Validated String address,
            @RequestParam(value = "hasGarden", required = false) @Validated Boolean hasGarden){
        return this.service.update(id, address, hasGarden);
    }

    @DeleteMapping("/delete/{id}")
    public Home remove (@PathVariable @Validated Integer id) {
        return this.service.remove(id);
    }



}
