package ru.otus.homework18.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework18.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment save(Comment comment);

    Optional<Comment> findById(long id);

    List<Comment> findAllByBookId(long bookId);

    void delete(Comment comment);

}