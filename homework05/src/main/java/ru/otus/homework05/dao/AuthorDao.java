package ru.otus.homework05.dao;

import ru.otus.homework05.model.Author;


public interface AuthorDao {
    Author getById(long id);
}
