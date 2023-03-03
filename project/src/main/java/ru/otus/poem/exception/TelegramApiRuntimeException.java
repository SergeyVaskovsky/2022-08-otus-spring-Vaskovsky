package ru.otus.poem.exception;

public class TelegramApiRuntimeException extends RuntimeException {
    public TelegramApiRuntimeException(Exception e) {
        super(e);
    }

}
