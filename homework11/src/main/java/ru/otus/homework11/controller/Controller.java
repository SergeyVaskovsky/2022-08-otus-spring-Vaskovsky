package ru.otus.homework11.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework11.mapping.AuthorDto;
import ru.otus.homework11.mapping.BookDto;
import ru.otus.homework11.mapping.CommentDto;
import ru.otus.homework11.mapping.GenreDto;
import ru.otus.homework11.model.Author;
import ru.otus.homework11.model.Book;
import ru.otus.homework11.model.Comment;
import ru.otus.homework11.model.Genre;
import ru.otus.homework11.repository.AuthorRepository;
import ru.otus.homework11.repository.BookRepository;
import ru.otus.homework11.repository.CommentRepository;
import ru.otus.homework11.repository.GenreRepository;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;

    @GetMapping(path = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<BookDto> getBooks() {
        return bookRepository
                .findAll()
                .map(BookDto::toDto);
    }

    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable String id) {
        bookRepository.deleteById(id).subscribe();
    }

    @GetMapping(path = "/books/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BookDto> getBook(@PathVariable String id) {
        return bookRepository.findById(id).map(BookDto::toDto);
    }

    @PostMapping("/books")
    public void createBook(@RequestBody BookDto bookDto) {
        Mono<Author> author = authorRepository.findById(bookDto.getAuthorId());
        Mono<Genre> genre = genreRepository.findById(bookDto.getGenreId());
        Flux
                .zip(author, genre)
                .map(t -> new Book(bookDto.getName(), t.getT1(), t.getT2()))
                .subscribe(book -> bookRepository.save(book).subscribe());
    }

    @PutMapping("/books/{id}")
    public void updateBook(@PathVariable String id, @RequestBody BookDto bookDto) {
        Mono<Author> author = authorRepository.findById(bookDto.getAuthorId());
        Mono<Genre> genre = genreRepository.findById(bookDto.getGenreId());
        Flux
                .zip(author, genre)
                .map(t -> new Book(id, bookDto.getName(), t.getT1(), t.getT2()))
                .subscribe(book -> bookRepository.save(book).subscribe());
    }

    @GetMapping(path = "/books/authors", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<AuthorDto> getAuthors() {
        return authorRepository.findAll()
                .map(a ->
                        new AuthorDto(a.getId(), a.getName())
                );
    }

    @GetMapping(path = "/books/genres", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<GenreDto> getGenres() {
        return genreRepository
                .findAll()
                .map(g -> new GenreDto(g.getId(), g.getName()));
    }

    @GetMapping(path = "/books/{id}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<CommentDto> getComments(@PathVariable String id) {
        return commentRepository
                .findAllByBookId(id)
                .map(CommentDto::toDto);
    }

    @PostMapping(path = "/books/{bookId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<CommentDto> addComment(@PathVariable String bookId, @RequestBody String description) {
        Mono<Comment> comment = Mono.from(bookRepository.findById(bookId))
                .map(book -> {
                    return commentRepository.save(new Comment(description, book));
                });
    }

    @DeleteMapping("/books/comments/{id}")
    public void deleteComment(@PathVariable String id) {
        commentRepository.deleteById(id).subscribe();
    }
}
