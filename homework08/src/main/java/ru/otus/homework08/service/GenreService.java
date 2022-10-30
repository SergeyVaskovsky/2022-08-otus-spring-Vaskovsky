package ru.otus.homework08.service;

import ru.otus.homework08.model.Genre;

import java.util.List;

public interface GenreService {
        List<Genre> getAll();

        Genre getById(String id);
}
