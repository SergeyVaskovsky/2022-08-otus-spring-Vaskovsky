package ru.otus.homework07.service;

import ru.otus.homework07.model.Genre;

import java.util.List;

public interface GenreService {
        List<Genre> getAll();

        Genre getById(long id);
}
