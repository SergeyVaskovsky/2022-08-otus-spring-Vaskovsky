package ru.otus.homework05.service;

import ru.otus.homework05.model.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAll();
    Author getById(long id);
}
