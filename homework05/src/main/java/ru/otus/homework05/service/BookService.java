package ru.otus.homework05.service;

import ru.otus.homework05.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAll();

    void insert(long bookId, String bookName, long authorId, long genreIdId);

    void delete(long bookId);

    void update(long bookId, String bookName, long authorId, long genreId);
}
