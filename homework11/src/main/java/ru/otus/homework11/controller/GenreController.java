package ru.otus.homework11.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.homework11.mapping.GenreDto;
import ru.otus.homework11.repository.GenreRepository;

@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreRepository genreRepository;

    @GetMapping(path = "/books/genres", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<GenreDto> getGenres() {
        return genreRepository
                .findAll()
                .map(g -> new GenreDto(g.getId(), g.getName()));
    }
}
