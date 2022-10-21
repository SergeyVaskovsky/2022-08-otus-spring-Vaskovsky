package ru.otus.homework05.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework05.dao.AuthorDaoJdbc;
import ru.otus.homework05.model.Author;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDaoJdbc authorDaoJdbc;

    public List<Author> getAll() {
        return authorDaoJdbc.getAll();
    }

    public Author getById(long id) {
        return authorDaoJdbc.getById(id);
    }
}
