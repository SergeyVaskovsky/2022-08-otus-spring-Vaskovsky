package ru.otus.homework16.service;

import ru.otus.homework16.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getAll(long bookId);

    Comment upsert(long commentId, String description, long bookId);

    void delete(long commentId);

    void deleteAll(Iterable<Comment> comments);
}
