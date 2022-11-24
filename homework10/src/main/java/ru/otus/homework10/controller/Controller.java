package ru.otus.homework10.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework10.model.Author;
import ru.otus.homework10.model.Book;
import ru.otus.homework10.model.Comment;
import ru.otus.homework10.model.Genre;
import ru.otus.homework10.service.AuthorService;
import ru.otus.homework10.service.BookService;
import ru.otus.homework10.service.CommentService;
import ru.otus.homework10.service.GenreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;

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
    public ResponseEntity createBook(@RequestBody Book book) {
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

    @GetMapping("/books/authors")
    public List<Author> getAuthors() {
        return authorService.getAll();
    }

    @GetMapping("/books/genres")
    public List<Genre> getGenres() {
        return genreService.getAll();
    }

    @GetMapping("/books/comments/{id}")
    public List<Comment> getComments(@PathVariable Long id) {
        return commentService.getAll(id);
    }

    @PostMapping("/books/{bookId}/comments")
    public Comment addComment(@PathVariable Long bookId, @RequestBody String description) {
        return commentService.upsert(0, description, bookId);
    }

    @DeleteMapping("/books/comments/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.delete(id);
    }
}
