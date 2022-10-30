package ru.otus.homework08.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework08.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, Long> {

    Book save(Book book);

    Optional<Book> findById(String id);

    List<Book> findAll();

    void delete(Book book);
}
