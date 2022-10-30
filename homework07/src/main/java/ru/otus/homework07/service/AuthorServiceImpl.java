package ru.otus.homework07.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework07.exception.AuthorNotFoundException;
import ru.otus.homework07.model.Author;
import ru.otus.homework07.repository.AuthorRepository;

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
