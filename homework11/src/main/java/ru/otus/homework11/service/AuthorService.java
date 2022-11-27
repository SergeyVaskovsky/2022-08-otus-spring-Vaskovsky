package ru.otus.homework11.service;

import ru.otus.homework11.model.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAll();

    Author getById(long id);
}
