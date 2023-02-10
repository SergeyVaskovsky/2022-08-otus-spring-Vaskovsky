package ru.otus.homework18.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework18.exception.BookNotFoundException;
import ru.otus.homework18.model.Author;
import ru.otus.homework18.model.Book;
import ru.otus.homework18.model.Genre;
import ru.otus.homework18.repository.BookRepository;

import javax.transaction.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookRepository bookRepository;
    private final CommentService commentService;
    private final RandomTimeoutService randomTimeoutService;

    @HystrixCommand(fallbackMethod = "buildFallbackBooks")
    @Override
    public List<Book> getAll() {
        randomTimeoutService.sleepRandomTimeout();
        return bookRepository.findAll();
    }

    public List<Book> buildFallbackBooks() {
        Book book = new Book();
        book.setId(0L);
        book.setName("По техническим причинам список книг недоступен");
        book.setAuthor(new Author(0L, "Н/Д"));
        book.setGenre(new Genre(0L, "Н/Д"));
        List<Book> books = List.of(book);
        return books;
    }

    @HystrixCommand(fallbackMethod = "buildFallbackBook")
    @Override
    public Book getById(long bookId) {
        randomTimeoutService.sleepRandomTimeout();
        return bookRepository.findWithAuthorWithGenreById(bookId).orElseThrow(() -> new BookNotFoundException(
                String.format("Book with id = %d not found", bookId)
        ));
    }

    public Book buildFallbackBook(long bookId) {
        Book book = new Book();
        book.setId(0L);
        book.setName("По техническим причинам книга недоступна");
        book.setAuthor(new Author(0L, "Н/Д"));
        book.setGenre(new Genre(0L, "Н/Д"));
        return book;
    }

    @HystrixCommand(fallbackMethod = "buildFallbackUpsert")
    @Override
    public Book upsert(long bookId, String bookName, long authorId, long genreId) {
        randomTimeoutService.sleepRandomTimeout();
        Author author = authorService.getById(authorId);
        Genre genre = genreService.getById(genreId);
        Book book = new Book(bookId, bookName, author, genre);
        bookRepository.save(book);
        return book;
    }

    public Book buildFallbackUpsert(long bookId, String bookName, long authorId, long genreId) {
        Book book = new Book();
        book.setId(0L);
        book.setName("По техническим причинам книга недоступна");
        book.setAuthor(new Author(0L, "Н/Д"));
        book.setGenre(new Genre(0L, "Н/Д"));
        return book;
    }

    @Transactional
    @Override
    public void delete(long bookId) {
        bookRepository.findById(bookId);
        commentService.deleteAllByBookId(bookId);
        bookRepository.deleteById(bookId);
    }
}
