package ru.otus.homework18.service;

import ru.otus.homework18.model.Genre;

import java.util.List;

public interface GenreService {
        List<Genre> getAll();

        Genre getById(long id);
}
