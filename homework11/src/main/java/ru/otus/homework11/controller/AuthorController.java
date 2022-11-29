package ru.otus.homework11.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.homework11.mapping.AuthorDto;
import ru.otus.homework11.repository.AuthorRepository;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorRepository authorRepository;

    @GetMapping(path = "/books/authors", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<AuthorDto> getAuthors() {
        return authorRepository.findAll()
                .map(a ->
                        new AuthorDto(a.getId(), a.getName())
                );
    }
}
