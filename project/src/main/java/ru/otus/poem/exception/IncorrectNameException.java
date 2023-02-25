package ru.otus.poem.exception;

public class IncorrectNameException extends RuntimeException {
    public IncorrectNameException(String s) {
        super(s);
    }
}
