package ru.otus.homework05.command;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework05.model.Author;
import ru.otus.homework05.model.Book;
import ru.otus.homework05.model.Genre;
import ru.otus.homework05.service.AuthorService;
import ru.otus.homework05.service.BookService;
import ru.otus.homework05.service.GenreService;
import ru.otus.homework05.service.OutputService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class Commands {

    private final BookService bookService;
    private final OutputService outputService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @ShellMethod(value = "Books", key = {"b", "books"})
    public void getBooks() {
        List<Book> books = bookService.getAll();
        books.forEach(book ->
                outputService.outputString(String.format("id: %d, name: %s, author: %s, genre: %s",
                        book.getId(),
                        book.getName(),
                        book.getAuthor().getName(),
                        book.getGenre().getName()))
        );
    }

    @ShellMethod(value = "Insert, params delimited by space: book id, book name, author id, genre id", key = {"i", "insert"})
    public void insertBook(
            @ShellOption() int bookId,
            @ShellOption() String bookName,
            @ShellOption() int authorId,
            @ShellOption() int genreId
    ) {
        bookService.insert(bookId, bookName, authorId, genreId);
    }

    @ShellMethod(value = "Delete, params: book id", key = {"d", "delete"})
    public void deleteBook(
            @ShellOption() int bookId
    ) {
        bookService.delete(bookId);
    }

    @ShellMethod(value = "Update, params delimited by space: book id, book name, author id, genre id", key = {"u", "update"})
    public void updateBook(
            @ShellOption() int bookId,
            @ShellOption() String bookName,
            @ShellOption() int authorId,
            @ShellOption() int genreId
    ) {
        bookService.update(bookId, bookName, authorId, genreId);
    }

    @ShellMethod(value = "Authors", key = {"a", "authors"})
    public void getAuthors() {
        List<Author> authors = authorService.getAll();
        authors.forEach(book ->
                outputService.outputString(String.format("id: %d, name: %s",
                        book.getId(),
                        book.getName()))
        );
    }

    @ShellMethod(value = "Genres", key = {"g", "genres"})
    public void getGenres() {
        List<Genre> genres = genreService.getAll();
        genres.forEach(book ->
                outputService.outputString(String.format("id: %d, name: %s",
                        book.getId(),
                        book.getName()))
        );
    }
}

