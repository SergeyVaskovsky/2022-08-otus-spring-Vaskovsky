package ru.otus.homework05.service;

import ru.otus.homework05.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAll();

    void insert(int bookId, String bookName, int authorId, int genreIdId);

    void delete(int bookId);

    void update(int bookId, String bookName, int authorId, int genreId);
}
