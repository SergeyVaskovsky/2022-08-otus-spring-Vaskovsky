package ru.otus.poem.exception;

public class PoemNotFoundException extends RuntimeException {

    public PoemNotFoundException(String message) {
        super(message);
    }
}
