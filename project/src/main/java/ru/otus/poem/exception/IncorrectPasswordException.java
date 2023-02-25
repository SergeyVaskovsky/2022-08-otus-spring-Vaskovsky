package ru.otus.poem.exception;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException(String s) {
        super(s);
    }
}
