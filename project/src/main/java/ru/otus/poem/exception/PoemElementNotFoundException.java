package ru.otus.poem.exception;

public class PoemElementNotFoundException extends RuntimeException {
    public PoemElementNotFoundException(String message) {
        super(message);
    }
}
