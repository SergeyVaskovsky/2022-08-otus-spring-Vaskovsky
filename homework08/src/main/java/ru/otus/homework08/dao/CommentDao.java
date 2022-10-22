package ru.otus.homework08.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework08.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDao extends MongoRepository<Comment, Long> {

    Comment save(Comment comment);

    Optional<Comment> findById(long id);

    List<Comment> findAllByBookId(long bookId);

    void delete(Comment comment);

}
