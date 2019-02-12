package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class CarExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(CarException.class)  //CarExceptionAdvice risponderà solo ad erroridì di tipo CarException
    @ResponseStatus(HttpStatus.NOT_FOUND) //HTTP 404
    String carNotFoundHandler(CarException ex) {
        return ex.getMessage();
    }
}
