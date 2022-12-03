package ru.otus.homework10.service;

import ru.otus.homework10.model.Genre;

import java.util.List;

public interface GenreService {
        List<Genre> getAll();

        Genre getById(long id);
}
