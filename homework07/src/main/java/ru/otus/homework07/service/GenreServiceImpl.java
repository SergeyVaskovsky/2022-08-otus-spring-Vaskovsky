package ru.otus.homework07.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework07.exception.GenreNotFoundException;
import ru.otus.homework07.model.Genre;
import ru.otus.homework07.repository.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    public Genre getById(long id) {
        return genreRepository
                .findById(id)
                .orElseThrow(() -> new GenreNotFoundException(String.format("Genre not found by id = %s", id)));
    }

}
