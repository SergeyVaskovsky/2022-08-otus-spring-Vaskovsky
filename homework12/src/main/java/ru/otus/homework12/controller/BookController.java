package ru.otus.homework12.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework12.mapping.BookDto;
import ru.otus.homework12.model.Book;
import ru.otus.homework12.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/api/books")
    public List<BookDto> getBooks() {
        return bookService
                .getAll()
                .stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/api/books/{id}")
    public void deleteBook(@PathVariable long id) {
        bookService.delete(id);
    }

    @GetMapping("/api/books/{id}")
    public BookDto getBook(@PathVariable long id) {
        Book b = bookService.getById(id);
        return BookDto.toDto(b);
    }

    @PostMapping("/api/books")
    public void createBook(@RequestBody BookDto book) {
        bookService.upsert(
                -1L,
                book.getName(),
                book.getAuthorId(),
                book.getGenreId());
    }

    @PutMapping("/api/books/{id}")
    public void updateBook(@PathVariable Long id, @RequestBody BookDto book) {
        bookService.upsert(
                id,
                book.getName(),
                book.getAuthorId(),
                book.getGenreId());
    }
}
