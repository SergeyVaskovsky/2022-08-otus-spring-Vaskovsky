package ru.otus.homework18.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework18.exception.GenreNotFoundException;
import ru.otus.homework18.model.Genre;
import ru.otus.homework18.repository.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final RandomTimeoutService randomTimeoutService;

    @HystrixCommand(fallbackMethod = "buildFallbackGenres")
    public List<Genre> getAll() {
        randomTimeoutService.sleepRandomTimeout();
        return genreRepository.findAll();
    }

    public List<Genre> buildFallbackGenres() {
        Genre genre = new Genre(0L, "Нет данных");
        return List.of(genre);
    }

    public Genre getById(long id) {
        return genreRepository
                .findById(id)
                .orElseThrow(() -> new GenreNotFoundException(String.format("Genre not found by id = %s", id)));
    }

}
