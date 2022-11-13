package ru.otus.homework10.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework10.exception.AuthorNotFoundException;
import ru.otus.homework10.model.Author;
import ru.otus.homework10.repository.AuthorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    public Author getById(long id) {
        return authorRepository
                .findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(String.format("Author not found by id = %s", id)));
    }
}
