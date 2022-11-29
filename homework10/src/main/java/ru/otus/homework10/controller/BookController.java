package ru.otus.homework10.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework10.mapping.BookDto;
import ru.otus.homework10.model.Book;
import ru.otus.homework10.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/books")
    public List<BookDto> getBooks() {
        return bookService
                .getAll()
                .stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable long id) {
        bookService.delete(id);
    }

    @GetMapping("/books/{id}")
    public BookDto getBook(@PathVariable long id) {
        Book b = bookService.getById(id);
        return BookDto.toDto(b);
    }

    @PostMapping("/books")
    public void createBook(@RequestBody BookDto book) {
        bookService.upsert(
                -1L,
                book.getName(),
                book.getAuthorId(),
                book.getGenreId());
    }

    @PutMapping("/books/{id}")
    public void updateBook(@PathVariable Long id, @RequestBody BookDto book) {
        bookService.upsert(
                id,
                book.getName(),
                book.getAuthorId(),
                book.getGenreId());
    }
}
