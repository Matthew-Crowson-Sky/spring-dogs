package com.sky.springdogs.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No dog found with that ID")
public class DogNotFoundException extends RuntimeException{
    public DogNotFoundException() {
    }

    public DogNotFoundException(String message) {
        super(message);
    }
}
