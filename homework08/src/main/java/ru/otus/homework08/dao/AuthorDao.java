package ru.otus.homework08.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework08.model.Author;

import java.util.List;
import java.util.Optional;


public interface AuthorDao extends MongoRepository<Author, Long> {
    List<Author> findAll();

    Optional<Author> findById(long id);
}
