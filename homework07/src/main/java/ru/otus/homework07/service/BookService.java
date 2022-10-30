package ru.otus.homework07.service;

import ru.otus.homework07.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAll();

    Book getById(long bookId);

    Book upsert(long bookId, String bookName, long authorId, long genreIdId);

    void delete(long bookId);

}
