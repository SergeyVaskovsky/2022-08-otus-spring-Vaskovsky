package ru.otus.homework16.service;

import ru.otus.homework16.model.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAll();

    Author getById(long id);
}
