package ru.otus.homework06.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework06.dao.BookDaoJpa;
import ru.otus.homework06.model.Author;
import ru.otus.homework06.model.Book;
import ru.otus.homework06.model.Genre;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookDaoJpa bookDaoJpa;

    @Override
    public List<Book> getAll() {
        return bookDaoJpa.findAll();
    }

    @Transactional
    @Override
    public Book upsert(long bookId, String bookName, long authorId, long genreId) {
        Author author = authorService.getById(authorId);
        Genre genre = genreService.getById(genreId);
        Book book = new Book(bookId, bookName, author, genre);
        bookDaoJpa.save(book);
        return book;
    }

    @Transactional
    @Override
    public void delete(long bookId) {
        Book book = bookDaoJpa.findById(bookId).orElse(null);
        if (isNull(book)) {
            return;
        }
        bookDaoJpa.delete(book);
    }

}
