package ru.otus.homework11.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.homework11.model.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {
}
