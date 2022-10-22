package ru.otus.homework07.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework07.dao.GenreDao;
import ru.otus.homework07.exception.GenreNotFoundException;
import ru.otus.homework07.model.Genre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    public List<Genre> getAll() {
        return genreDao.findAll();
    }

    public Genre getById(long id) {
        return genreDao
                .findById(id)
                .orElseThrow(() -> new GenreNotFoundException(String.format("Genre not found by id = %s", id)));
    }

}
