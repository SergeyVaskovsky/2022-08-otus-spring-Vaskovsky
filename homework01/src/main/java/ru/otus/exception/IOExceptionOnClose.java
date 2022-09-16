package ru.otus.exception;

public class IOExceptionOnClose extends RuntimeException {
    public IOExceptionOnClose(String message) {
        super(message);
    }
}
