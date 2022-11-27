package ru.otus.homework11.service;

import ru.otus.homework11.model.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> getAll();

    Genre getById(long id);
}
