package ru.otus.homework13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework13.mapping.GenreDto;
import ru.otus.homework13.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/api/genres")
    public List<GenreDto> getGenres() {
        return genreService
                .getAll()
                .stream()
                .map(g -> new GenreDto(g.getId(), g.getName()))
                .collect(Collectors.toList());
    }
}
