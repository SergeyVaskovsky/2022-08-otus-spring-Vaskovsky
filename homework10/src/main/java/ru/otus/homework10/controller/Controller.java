package ru.otus.homework10.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework10.mapping.AuthorDto;
import ru.otus.homework10.mapping.BookDto;
import ru.otus.homework10.mapping.CommentDto;
import ru.otus.homework10.mapping.GenreDto;
import ru.otus.homework10.model.Book;
import ru.otus.homework10.model.Comment;
import ru.otus.homework10.service.AuthorService;
import ru.otus.homework10.service.BookService;
import ru.otus.homework10.service.CommentService;
import ru.otus.homework10.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;

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

    @GetMapping("/books/authors")
    public List<AuthorDto> getAuthors() {
        return authorService
                .getAll()
                .stream()
                .map(a -> new AuthorDto(a.getId(), a.getName()))
                .collect(Collectors.toList());
    }

    @GetMapping("/books/genres")
    public List<GenreDto> getGenres() {
        return genreService
                .getAll()
                .stream()
                .map(g -> new GenreDto(g.getId(), g.getName()))
                .collect(Collectors.toList());
    }

    @GetMapping("/books/{id}/comments")
    public List<CommentDto> getComments(@PathVariable Long id) {
        return commentService
                .getAll(id)
                .stream()
                .map(CommentDto::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/books/{bookId}/comments")
    public CommentDto addComment(@PathVariable Long bookId, @RequestBody String description) {
        Comment comment = commentService.upsert(0, description, bookId);
        return CommentDto.toDto(comment);
    }

    @DeleteMapping("/books/comments/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.delete(id);
    }
}
