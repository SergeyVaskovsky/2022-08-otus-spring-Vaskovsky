package ru.otus.homework06.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework06.dao.AuthorDaoJpa;
import ru.otus.homework06.exception.AuthorNotFoundException;
import ru.otus.homework06.model.Author;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDaoJpa authorDaoJpa;

    public List<Author> getAll() {
        return authorDaoJpa.findAll();
    }

    public Author getById(long id) {
        return authorDaoJpa
                .findById(id)
                .orElseThrow( ()-> new AuthorNotFoundException(String.format("Author not found by id = %s", id)));
    }
}
