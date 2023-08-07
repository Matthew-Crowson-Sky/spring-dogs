package com.sky.springdogs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No home found with that ID")
public class HomeNotFoundException extends RuntimeException{
    public HomeNotFoundException() {}
    public HomeNotFoundException(String message) {
        super(message);
    }
}
