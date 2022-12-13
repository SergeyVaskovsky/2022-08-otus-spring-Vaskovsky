package ru.otus.homework11.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.homework11.model.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
}
