package ru.otus.homework11.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework11.mapping.CommentDto;
import ru.otus.homework11.model.Comment;
import ru.otus.homework11.repository.BookRepository;
import ru.otus.homework11.repository.CommentRepository;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @GetMapping(path = "/api/books/{id}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<CommentDto> getComments(@PathVariable String id) {
        return commentRepository
                .findAllByBookId(id)
                .map(CommentDto::toDto);
    }

    @PostMapping(path = "/api/books/{bookId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<CommentDto> addComment(@PathVariable String bookId, @RequestBody String description) {
        return Mono.from(bookRepository.findById(bookId))
                .flatMap(book -> commentRepository.save(new Comment(description, book)).map(CommentDto::toDto));

    }

    @DeleteMapping("/api/books/comments/{id}")
    public void deleteComment(@PathVariable String id) {
        commentRepository.deleteById(id).subscribe();
    }
}
