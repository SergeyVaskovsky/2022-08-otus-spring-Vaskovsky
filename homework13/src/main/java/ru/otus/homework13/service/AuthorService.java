package ru.otus.homework13.service;

import ru.otus.homework13.model.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAll();

    Author getById(long id);
}
