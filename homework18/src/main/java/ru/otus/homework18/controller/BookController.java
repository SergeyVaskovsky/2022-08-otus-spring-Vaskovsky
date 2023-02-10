package ru.otus.homework18.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework18.mapping.BookDto;
import ru.otus.homework18.model.Book;
import ru.otus.homework18.service.BookService;
import ru.otus.homework18.service.CallTransactionalMethodService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final CallTransactionalMethodService callTransactionalMethodService;

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
        callTransactionalMethodService.delete(id);
    }

    @GetMapping("/api/books/{id}")
    public BookDto getBook(@PathVariable long id) {
        Book b = bookService.getById(id);
        return BookDto.toDto(b);
    }

    @PostMapping("/api/books")
    public BookDto createBook(@RequestBody BookDto book) {
        Book b = bookService.upsert(
                -1L,
                book.getName(),
                book.getAuthorId(),
                book.getGenreId());
        return BookDto.toDto(b);
    }

    @PutMapping("/api/books/{id}")
    public BookDto updateBook(@PathVariable Long id, @RequestBody BookDto book) {
        Book b = bookService.upsert(
                id,
                book.getName(),
                book.getAuthorId(),
                book.getGenreId());
        return BookDto.toDto(b);
    }
}
