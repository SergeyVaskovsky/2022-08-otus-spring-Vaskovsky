package ru.otus.homework13.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;
import ru.otus.homework13.exception.GenreNotFoundException;
import ru.otus.homework13.model.Genre;
import ru.otus.homework13.repository.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @PostFilter("hasPermission(filterObject, 'READ')")
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @PostAuthorize("hasPermission(returnObject, 'READ')")
    public Genre getById(long id) {
        Genre genre = genreRepository.findById(id);
        if (genre == null) {
            throw new GenreNotFoundException(String.format("Genre not found by id = %s", id));
        }
        return genre;

    }

}
