package ru.otus.homework05.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework05.dao.BookDaoJdbc;
import ru.otus.homework05.model.Author;
import ru.otus.homework05.model.Book;
import ru.otus.homework05.model.Genre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookDaoJdbc bookDaoJdbc;

    @Override
    public List<Book> getAll() {
        return bookDaoJdbc.getAll();
    }

    @Override
    public void insert(String bookName, long authorId, long genreId) {
        Author author = authorService.getById(authorId);
        Genre genre = genreService.getById(genreId);
        Book book = new Book(0L, bookName, author, genre);
        bookDaoJdbc.insert(book);
    }

    @Override
    public void delete(long bookId) {
        bookDaoJdbc.deleteById(bookId);
    }

    @Override
    public void update(long bookId, String bookName, long authorId, long genreId) {
        Author author = authorService.getById(authorId);
        Genre genre = genreService.getById(genreId);
        Book book = new Book(bookId, bookName, author, genre);
        bookDaoJdbc.update(book);
    }
}
