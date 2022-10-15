package ru.otus.homework05.service;

import ru.otus.homework05.model.Genre;

import java.util.List;

public interface GenreService {
        List<Genre> getAll();

        Genre getById(long id);
}
