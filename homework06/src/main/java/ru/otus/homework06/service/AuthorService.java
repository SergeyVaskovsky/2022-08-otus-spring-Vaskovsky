package ru.otus.homework06.service;

import ru.otus.homework06.model.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAll();
    Author getById(long id);
}
