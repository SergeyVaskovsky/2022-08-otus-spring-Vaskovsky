package ru.otus.poem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.poem.model.dto.CommentDto;
import ru.otus.poem.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/comments")
    public CommentDto addNewComment(@RequestBody CommentDto commentDto) {
        return commentService.addNewComment(commentDto);
    }

    @PutMapping("/api/comments/{id}")
    public CommentDto updateComment(@PathVariable Long id, @RequestBody CommentDto commentDto) {
        return commentService.updateComment(id, commentDto);
    }

    @DeleteMapping("/api/comments/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteById(id);
    }

    @GetMapping("/api/comments/poem/{id}")
    public List<CommentDto> getAllCommentByPoem(@PathVariable Long id) {
        return commentService.getAllByPoemId(id);
    }
}
