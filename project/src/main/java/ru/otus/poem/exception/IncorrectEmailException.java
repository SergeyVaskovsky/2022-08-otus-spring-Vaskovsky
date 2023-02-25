package ru.otus.poem.exception;

public class IncorrectEmailException extends RuntimeException {
    public IncorrectEmailException(String s) {
        super(s);
    }
}
