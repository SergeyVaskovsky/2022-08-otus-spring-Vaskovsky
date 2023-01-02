package ru.otus.homework13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework13.mapping.AuthorDto;
import ru.otus.homework13.service.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/api/authors")
    public List<AuthorDto> getAuthors() {
        return authorService
                .getAll()
                .stream()
                .map(a -> new AuthorDto(a.getId(), a.getName()))
                .collect(Collectors.toList());
    }
}
