package ru.otus.homework08.service;

import ru.otus.homework08.model.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAll();
    Author getById(long id);
}
