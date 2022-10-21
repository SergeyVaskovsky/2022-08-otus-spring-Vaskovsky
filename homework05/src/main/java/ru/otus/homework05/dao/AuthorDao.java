package ru.otus.homework05.dao;

import ru.otus.homework05.model.Author;

import java.util.List;


public interface AuthorDao {
    List<Author> getAll();

    Author getById(long id);
}
