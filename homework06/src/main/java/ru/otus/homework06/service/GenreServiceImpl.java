package ru.otus.homework06.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework06.dao.GenreDaoJpa;
import ru.otus.homework06.exception.AuthorNotFoundException;
import ru.otus.homework06.exception.GenreNotFoundException;
import ru.otus.homework06.model.Genre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreDaoJpa genreDaoJpa;

    public List<Genre> getAll() {
        return genreDaoJpa.findAll();
    }

    public Genre getById(long id) {
        return genreDaoJpa
                .findById(id)
                .orElseThrow( ()-> new GenreNotFoundException(String.format("Genre not found by id = %s", id)));
    }

}
