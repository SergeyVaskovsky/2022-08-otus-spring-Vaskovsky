package ru.otus.poem.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.poem.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = {"user", "user.roles", "poem", "rootComment"})
    Optional<Comment> findById(Long id);

    @EntityGraph(attributePaths = {"user"})
    List<Comment> findAllByPoemId(Long id);
}
