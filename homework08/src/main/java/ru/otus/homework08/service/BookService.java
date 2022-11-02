package ru.otus.homework08.service;

import ru.otus.homework08.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAll();

    Book getById(String bookId);

    Book upsert(String bookId, String bookName, String authorId, String genreIdId);

    void delete(String bookId);

}
