package ru.otus.homework06.dao;

import ru.otus.homework06.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDao {

    Comment save(Comment comment);

    Optional<Comment> findById(long id);

    List<Comment> findAll(long bookId);

    void delete(Comment comment);

}
