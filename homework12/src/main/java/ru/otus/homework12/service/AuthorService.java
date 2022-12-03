package ru.otus.homework12.service;

import ru.otus.homework12.model.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAll();

    Author getById(long id);
}
