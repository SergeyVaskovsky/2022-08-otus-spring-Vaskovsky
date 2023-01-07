package ru.otus.homework16.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.homework16.model.Comment;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "comments")
public interface CommentRepository extends JpaRepository<Comment, Long> {
    //@RestResource(path = "bookComments", rel = "bookComments")
    //List<Comment> findAllByBookId(long bookId);
}
