package ru.otus.homework08.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework08.repository.BookRepository;
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
    private final BookRepository bookRepository;
    private final DeleteCommentService commentService;

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book getById(String bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(
                String.format("Book with id = %s not found", bookId)
        ));
    }

    @Override
    public Book upsert(String bookId, String bookName, String authorId, String genreId) {
        Author author = authorService.getById(authorId);
        Genre genre = genreService.getById(genreId);
        Book book;
        if ("".equals(bookId)) {
            book = new Book(bookName, author, genre);
        } else {
            book = new Book(bookId, bookName, author, genre);
        }
        bookRepository.save(book);
        return book;
    }

    @Override
    @Transactional
    public void delete(String bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (isNull(book)) {
            return;
        }
        commentService.deleteAllByBookId(bookId);
        bookRepository.delete(book);
    }

}
