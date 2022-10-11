package ru.otus.homework05.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework05.dao.AuthorDaoJdbc;
import ru.otus.homework05.dao.BookDaoJdbc;
import ru.otus.homework05.dao.GenreDaoJdbc;
import ru.otus.homework05.model.Author;
import ru.otus.homework05.model.Book;
import ru.otus.homework05.model.Genre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final AuthorDaoJdbc authorDaoJdbc;
    private final GenreDaoJdbc genreDaoJdbc;
    private final BookDaoJdbc bookDaoJdbc;

    @Override
    public List<Book> getAll() {
        return bookDaoJdbc.getAll();
    }

    @Override
    public void insert(int bookId, String bookName, int authorId, int genreId) {
        Author author = authorDaoJdbc.getById(authorId);
        Genre genre = genreDaoJdbc.getById(genreId);
        Book book = new Book(bookId, bookName, author, genre);
        bookDaoJdbc.insert(book);
    }

    @Override
    public void delete(int bookId) {
        bookDaoJdbc.deleteById(bookId);
    }

    @Override
    public void update(int bookId, String bookName, int authorId, int genreId) {
        Author author = authorDaoJdbc.getById(authorId);
        Genre genre = genreDaoJdbc.getById(genreId);
        Book book = new Book(bookId, bookName, author, genre);
        bookDaoJdbc.update(book);
    }
}
