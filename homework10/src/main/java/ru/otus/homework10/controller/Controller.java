package ru.otus.homework10.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework10.model.Author;
import ru.otus.homework10.model.Book;
import ru.otus.homework10.service.AuthorService;
import ru.otus.homework10.service.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final BookService bookService;
    private final AuthorService authorService;

    @GetMapping("/api/book")
    public List<Book> getBooks() {
        return bookService.getAll();
    }

    @DeleteMapping("/api/book/{id}")
    public void deleteBook(@PathVariable long id) {
        bookService.delete(id);
    }

    @GetMapping("/api/book/{id}")
    public void getBook(@PathVariable long id) {
        bookService.getById(id);
    }

    @GetMapping("/api/author")
    public List<Author> getAuthors() {
        return authorService.getAll();
    }
}
