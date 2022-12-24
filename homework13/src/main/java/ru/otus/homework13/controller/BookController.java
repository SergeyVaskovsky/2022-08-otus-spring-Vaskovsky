package ru.otus.homework13.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework13.mapping.BookDto;
import ru.otus.homework13.model.Author;
import ru.otus.homework13.model.Book;
import ru.otus.homework13.model.Genre;
import ru.otus.homework13.service.AuthorService;
import ru.otus.homework13.service.BookService;
import ru.otus.homework13.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @GetMapping("/api/books")
    public List<BookDto> getBooks() {
        SecurityContextHolder.getContext().getAuthentication();
        return bookService
                .getAll()
                .stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/api/books/{id}")
    public void deleteBook(@PathVariable long id) {
        Book book = bookService.getById(id);
        bookService.delete(book);
    }

    @GetMapping("/api/books/{id}")
    public BookDto getBook(@PathVariable long id) {
        Book b = bookService.getById(id);
        return BookDto.toDto(b);
    }

    @PostMapping("/api/books")
    public void createBook(@RequestBody BookDto bookDto) {
        Author author = authorService.getById(bookDto.getAuthorId());
        Genre genre = genreService.getById(bookDto.getGenreId());
        Book book = new Book(-1L, bookDto.getName(), author, genre);
        bookService.upsert(book);
    }

    @PutMapping("/api/books/{id}")
    public void updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) {
        Author author = authorService.getById(bookDto.getAuthorId());
        Genre genre = genreService.getById(bookDto.getGenreId());
        Book book = new Book(id, bookDto.getName(), author, genre);
        bookService.upsert(book);
    }
}
