package ru.otus.homework14.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework14.model.rdb.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
