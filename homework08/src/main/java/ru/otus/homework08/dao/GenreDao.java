package ru.otus.homework08.dao;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework08.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao extends MongoRepository<Genre, Long> {
    List<Genre> findAll();

    Optional<Genre> findById(long id);
}
