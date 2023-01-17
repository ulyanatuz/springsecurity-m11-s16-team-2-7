package com.softserve.itacademy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidAccessException extends RuntimeException{

    public InvalidAccessException(String message) {
        super(message);
    }
}
