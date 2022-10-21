package ru.otus.homework07.dao;

import ru.otus.homework07.model.Author;

import java.util.List;
import java.util.Optional;


public interface AuthorDao {
    List<Author> findAll();

    Optional<Author> findById(long id);
}
