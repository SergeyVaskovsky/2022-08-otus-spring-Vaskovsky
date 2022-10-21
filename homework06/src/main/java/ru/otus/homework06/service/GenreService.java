package ru.otus.homework06.service;

import ru.otus.homework06.model.Genre;

import java.util.List;

public interface GenreService {
        List<Genre> getAll();

        Genre getById(long id);
}
