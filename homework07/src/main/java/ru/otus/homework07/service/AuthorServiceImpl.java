package ru.otus.homework07.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework07.dao.AuthorDao;
import ru.otus.homework07.exception.AuthorNotFoundException;
import ru.otus.homework07.model.Author;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    public List<Author> getAll() {
        return authorDao.findAll();
    }

    public Author getById(long id) {
        return authorDao
                .findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(String.format("Author not found by id = %s", id)));
    }
}
