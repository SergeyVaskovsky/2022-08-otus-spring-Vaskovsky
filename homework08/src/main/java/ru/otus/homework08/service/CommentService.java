package ru.otus.homework08.service;

import ru.otus.homework08.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getAll(String bookId);

    Comment upsert(String commentId, String description, String bookId);
}
