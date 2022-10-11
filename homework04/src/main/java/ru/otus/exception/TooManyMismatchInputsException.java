package ru.otus.exception;

public class TooManyMismatchInputsException extends RuntimeException {
    public TooManyMismatchInputsException(String message) {
        super(message);
    }
}
