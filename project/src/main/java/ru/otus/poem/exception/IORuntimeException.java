package ru.otus.poem.exception;

import java.io.IOException;

public class IORuntimeException extends RuntimeException {
    public IORuntimeException(IOException e) {
        super(e);
    }
}
