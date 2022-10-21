package ru.otus.homework06.dao;

import ru.otus.homework06.model.Author;

import java.util.List;
import java.util.Optional;


public interface AuthorDao {
    List<Author> findAll();

    Optional<Author> findById(long id);
}
