package ru.otus.homework10.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework10.mapping.CommentDto;
import ru.otus.homework10.model.Comment;
import ru.otus.homework10.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommentController {

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
        return CommentDto.toDto(comment);
    }

    @DeleteMapping("/api/books/comments/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.delete(id);
    }
}
