package ru.otus.homework05.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework05.model.Book;
import ru.otus.homework05.service.BookService;

import java.util.List;

@ShellComponent
@Slf4j
public class Commands {

    private static final int QUIT_CODE = 0;
    private final BookService bookService;

    @Autowired
    public Commands(BookService bookService) {
        this.bookService = bookService;
    }

    @ShellMethod(value = "Books", key = {"b", "books"})
    public void getBooks() {
        List<Book> books = bookService.getAll();
        books.forEach(book ->
                log.info(String.format("id: %d, name: %s, author: %s, genre: %s",
                        book.getId(),
                        book.getName(),
                        book.getAuthor().getName(),
                        book.getGenre().getName()))
        );
    }

    @ShellMethod(value = "Insert", key = {"i", "insert"})
    public void insertBook(
            @ShellOption() int bookId,
            @ShellOption() String bookName,
            @ShellOption() int authorId,
            @ShellOption() int genreId
    ) {
        bookService.insert(bookId, bookName, authorId, genreId);
    }

    @ShellMethod(value = "Delete", key = {"d", "delete"})
    public void deleteBook(
            @ShellOption() int bookId
    ) {
        bookService.delete(bookId);
    }

    @ShellMethod(value = "Update", key = {"u", "update"})
    public void updateBook(
            @ShellOption() int bookId,
            @ShellOption() String bookName,
            @ShellOption() int authorId,
            @ShellOption() int genreId
    ) {
        bookService.update(bookId, bookName, authorId, genreId);
    }

    @ShellMethod(value = "Quit", key = {"q", "quit"})
    public void quit() {
        System.exit(QUIT_CODE);
    }
}

