package ru.otus.homework11.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework11.mapping.BookDto;
import ru.otus.homework11.model.Author;
import ru.otus.homework11.model.Book;
import ru.otus.homework11.model.Genre;
import ru.otus.homework11.repository.AuthorRepository;
import ru.otus.homework11.repository.BookRepository;
import ru.otus.homework11.repository.GenreRepository;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;


    @GetMapping(path = "/api/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<BookDto> getBooks() {
        return bookRepository
                .findAll()
                .map(BookDto::toDto);
    }

    @DeleteMapping("/api/books/{id}")
    public void deleteBook(@PathVariable String id) {
        bookRepository.deleteById(id).subscribe();
    }

    @GetMapping(path = "/api/books/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BookDto> getBook(@PathVariable String id) {
        return bookRepository.findById(id).map(BookDto::toDto);
    }

    @PostMapping("/api/books")
    public void createBook(@RequestBody BookDto bookDto) {
        Mono<Author> author = authorRepository.findById(bookDto.getAuthorId());
        Mono<Genre> genre = genreRepository.findById(bookDto.getGenreId());
        Flux
                .zip(author, genre)
                .map(t -> new Book(bookDto.getName(), t.getT1(), t.getT2()))
                .subscribe(book -> bookRepository.save(book).subscribe());
    }

    @PutMapping("/api/books/{id}")
    public void updateBook(@PathVariable String id, @RequestBody BookDto bookDto) {
        Mono<Author> author = authorRepository.findById(bookDto.getAuthorId());
        Mono<Genre> genre = genreRepository.findById(bookDto.getGenreId());
        Flux
                .zip(author, genre)
                .map(t -> new Book(id, bookDto.getName(), t.getT1(), t.getT2()))
                .subscribe(book -> bookRepository.save(book).subscribe());
    }
}
