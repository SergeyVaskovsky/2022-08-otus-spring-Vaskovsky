package ru.otus.homework08.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework08.repository.AuthorRepository;
import ru.otus.homework08.exception.AuthorNotFoundException;
import ru.otus.homework08.model.Author;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    public Author getById(String id) {
        return authorRepository
                .findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(String.format("Author not found by id = %s", id)));
    }
}
