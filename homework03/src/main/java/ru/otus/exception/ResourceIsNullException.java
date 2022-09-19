package ru.otus.exception;

public class ResourceIsNullException extends RuntimeException {
    public ResourceIsNullException(String message) {
        super(message);
    }
}
