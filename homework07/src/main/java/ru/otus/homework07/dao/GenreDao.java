package ru.otus.homework07.dao;


import ru.otus.homework07.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    List<Genre> findAll();

    Optional<Genre> findById(long id);
}
