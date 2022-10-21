package ru.otus.homework07.dao;

import ru.otus.homework07.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    Book save(Book book);

    Optional<Book> findById(long id);

    List<Book> findAll();

    void delete(Book book);
}
