package ru.otus.homework07.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework07.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment save(Comment comment);

    @EntityGraph(attributePaths = {"book"})
    Optional<Comment> findById(long id);

    @EntityGraph(attributePaths = {"book"})
    List<Comment> findAllByBookId(long bookId);

    void delete(Comment comment);

}
