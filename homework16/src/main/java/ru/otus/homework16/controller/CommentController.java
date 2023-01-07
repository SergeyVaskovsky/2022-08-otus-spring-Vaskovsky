package ru.otus.homework16.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework16.mapping.CommentDto;
import ru.otus.homework16.model.Comment;
import ru.otus.homework16.service.CommentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private LocalDateTime lastAddCommentInstant = LocalDateTime.now();

    private final CommentService commentService;

    @GetMapping("/api/books/{id}/comments")
    public List<CommentDto> getComments(@PathVariable Long id) {
        return commentService
                .getAll(id)
                .stream()
                .map(CommentDto::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/api/books/{bookId}/comments")
    public CommentDto addComment(@PathVariable Long bookId, @RequestBody String description) {
        Comment comment = commentService.upsert(0, description, bookId);
        lastAddCommentInstant = LocalDateTime.now();
        return CommentDto.toDto(comment);
    }

    @DeleteMapping("/api/books/comments/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.delete(id);
    }

    public LocalDateTime getLastAddCommentInstant() {
        return lastAddCommentInstant;
    }
}