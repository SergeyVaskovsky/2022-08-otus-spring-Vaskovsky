package ru.otus.homework08.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework08.dao.BookDao;
import ru.otus.homework08.exception.BookNotFoundException;
import ru.otus.homework08.model.Author;
import ru.otus.homework08.model.Book;
import ru.otus.homework08.model.Genre;

import java.util.List;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookDao bookDao;

    @Override
    public List<Book> getAll() {
        return bookDao.findAll();
    }

    @Override
    public Book getById(long bookId) {
        return bookDao.findById(bookId).orElseThrow(() -> new BookNotFoundException(
                String.format("Book with id = %d not found", bookId)
        ));
    }

    @Override
    public Book upsert(long bookId, String bookName, long authorId, long genreId) {
        Author author = authorService.getById(authorId);
        Genre genre = genreService.getById(genreId);
        Book book = new Book(bookId, bookName, author, genre);
        bookDao.save(book);
        return book;
    }

    @Override
    public void delete(long bookId) {
        Book book = bookDao.findById(bookId).orElse(null);
        if (isNull(book)) {
            return;
        }
        bookDao.delete(book);
    }

}
