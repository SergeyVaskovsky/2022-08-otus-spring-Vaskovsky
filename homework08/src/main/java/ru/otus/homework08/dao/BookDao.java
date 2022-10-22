package ru.otus.homework08.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework08.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao extends MongoRepository<Book, Long> {

    Book save(Book book);

    Optional<Book> findById(long id);

    List<Book> findAll();

    void delete(Book book);
}
