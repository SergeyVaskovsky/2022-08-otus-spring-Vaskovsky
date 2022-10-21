package ru.otus.homework05.dao;

import ru.otus.homework05.model.Book;

import java.util.List;

public interface BookDao {
    long count();

    Book insert(Book book);

    void update(Book book);

    Book getById(long id);

    List<Book> getAll();

    void deleteById(long id);
}
