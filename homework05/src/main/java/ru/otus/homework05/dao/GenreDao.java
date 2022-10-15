package ru.otus.homework05.dao;


import ru.otus.homework05.model.Genre;

import java.util.List;

public interface GenreDao {
    List<Genre> getAll();

    Genre getById(long id);
}
