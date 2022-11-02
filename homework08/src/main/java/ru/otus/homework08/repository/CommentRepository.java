package ru.otus.homework08.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework08.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends MongoRepository<Comment, Long> {

    Comment save(Comment comment);

    Optional<Comment> findById(String id);

    List<Comment> findAllByBookId(String bookId);

    void delete(Comment comment);

    void deleteAllByBookId(String bookId);

}
