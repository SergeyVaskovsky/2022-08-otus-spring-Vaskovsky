package ru.otus.homework06.service;

import ru.otus.homework06.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAll();

    void upsert(long bookId, String bookName, long authorId, long genreIdId);

    void delete(long bookId);

}
