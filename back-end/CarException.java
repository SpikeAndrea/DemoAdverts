package com.example.demo;

//Classe che dovrebbe gestire le eccezioni.
//In questo caso solo quando non trova l'id

//E' collegata, per il nostro esempio alla
//CarExceptionAdvice.java
//dove viene espressa esplicitamente

public class CarException extends RuntimeException {
    CarException(Integer id){
        super("Not found car " + id);
    }
}
