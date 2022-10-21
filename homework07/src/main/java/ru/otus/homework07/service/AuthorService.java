package ru.otus.homework07.service;

import ru.otus.homework07.model.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAll();
    Author getById(long id);
}
