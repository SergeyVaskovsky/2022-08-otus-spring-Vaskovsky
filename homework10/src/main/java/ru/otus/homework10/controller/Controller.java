package ru.otus.homework10.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework10.model.Book;
import ru.otus.homework10.service.AuthorService;
import ru.otus.homework10.service.BookService;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final BookService bookService;
    private final AuthorService authorService;

    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookService.getAll();
    }

    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable long id) {
        bookService.delete(id);
    }

    @GetMapping("/books/{id}")
    public Book getBook(@PathVariable long id) {
        return bookService.getById(id);
    }

    @PostMapping("/books")
    public ResponseEntity createBook(@RequestBody Book book) throws URISyntaxException {
        Book savedBook = bookService.upsert(
                -1L,
                book.getName(),
                book.getAuthor().getId(),
                book.getGenre().getId());
        return ResponseEntity.ok(savedBook);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity updateClient(@PathVariable Long id, @RequestBody Book book) {
        Book savedBook = bookService.upsert(
                id,
                book.getName(),
                book.getAuthor().getId(),
                book.getGenre().getId());

        return ResponseEntity.ok(savedBook);
    }
}
