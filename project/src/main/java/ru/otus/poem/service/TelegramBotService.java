package ru.otus.poem.service;

public interface TelegramBotService {
    void moderate(String id);

    void delete(String id);
}
