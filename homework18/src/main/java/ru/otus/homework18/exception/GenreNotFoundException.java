package ru.otus.homework18.exception;

public class GenreNotFoundException extends RuntimeException {
    public GenreNotFoundException(String message) {
        super(message);
    }
}
