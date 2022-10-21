package ru.otus.homework05.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework05.dao.GenreDaoJdbc;
import ru.otus.homework05.model.Genre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreDaoJdbc genreDaoJdbc;

    public List<Genre> getAll() {
        return genreDaoJdbc.getAll();
    }

    public Genre getById(long id) {
        return genreDaoJdbc.getById(id);
    }

}
