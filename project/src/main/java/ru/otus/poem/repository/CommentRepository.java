package ru.otus.poem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.poem.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
