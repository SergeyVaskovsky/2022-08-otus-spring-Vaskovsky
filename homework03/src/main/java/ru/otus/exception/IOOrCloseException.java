package ru.otus.exception;

public class IOOrCloseException extends RuntimeException {
    public IOOrCloseException(String message) {
        super(message);
    }
}
