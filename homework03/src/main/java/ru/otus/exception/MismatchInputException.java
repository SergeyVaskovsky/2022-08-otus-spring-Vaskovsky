package ru.otus.exception;

public class MismatchInputException extends RuntimeException {
    public MismatchInputException(String message) {
        super(message);
    }
}
