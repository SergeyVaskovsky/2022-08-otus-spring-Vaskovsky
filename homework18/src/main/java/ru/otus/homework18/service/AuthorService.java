package ru.otus.homework18.service;

import ru.otus.homework18.model.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAll();

    Author getById(long id);
}
