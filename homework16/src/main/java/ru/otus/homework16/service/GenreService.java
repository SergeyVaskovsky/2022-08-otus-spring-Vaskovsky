package ru.otus.homework16.service;

import ru.otus.homework16.model.Genre;

import java.util.List;

public interface GenreService {
        List<Genre> getAll();

        Genre getById(long id);
}
