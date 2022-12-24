package ru.otus.homework13.service;

import ru.otus.homework13.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAll();

    Book getById(long bookId);

    Book upsert(Book book);

    void delete(Book book);
}
