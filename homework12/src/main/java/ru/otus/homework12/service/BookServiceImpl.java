package ru.otus.homework12.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework12.exception.BookNotFoundException;
import ru.otus.homework12.model.Author;
import ru.otus.homework12.model.Book;
import ru.otus.homework12.model.Genre;
import ru.otus.homework12.repository.BookRepository;

import javax.transaction.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookRepository bookRepository;
    private final CommentService commentService;

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book getById(long bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(
                String.format("Book with id = %d not found", bookId)
        ));
    }

    @Transactional
    @Override
    public Book upsert(long bookId, String bookName, long authorId, long genreId) {
        Author author = authorService.getById(authorId);
        Genre genre = genreService.getById(genreId);
        Book book = new Book(bookId, bookName, author, genre);
        bookRepository.save(book);
        return book;
    }

    @Transactional
    @Override
    public void delete(long bookId) {
        commentService.deleteAll(commentService.getAll(bookId));
        bookRepository.deleteById(bookId);
    }

}
