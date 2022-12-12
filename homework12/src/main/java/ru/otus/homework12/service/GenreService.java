package ru.otus.homework12.service;

import ru.otus.homework12.model.Genre;

import java.util.List;

public interface GenreService {
        List<Genre> getAll();

        Genre getById(long id);
}
