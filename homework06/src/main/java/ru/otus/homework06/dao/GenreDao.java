package ru.otus.homework06.dao;


import ru.otus.homework06.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    List<Genre> findAll();

    Optional<Genre> findById(long id);
}
