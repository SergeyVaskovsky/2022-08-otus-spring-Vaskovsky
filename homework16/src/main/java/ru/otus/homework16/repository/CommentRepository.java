package ru.otus.homework16.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.homework16.model.Comment;

@RepositoryRestResource(path = "comments")
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
