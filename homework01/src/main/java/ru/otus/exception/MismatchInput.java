package ru.otus.exception;

public class MismatchInput extends RuntimeException {
    public MismatchInput(String message) {
        super(message);
    }
}
