package ru.otus.homework06.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework06.model.Author;
import ru.otus.homework06.model.Book;
import ru.otus.homework06.model.Comment;
import ru.otus.homework06.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({CommentDaoJpa.class})
public class CommentDaoJpaTest {

    @Autowired
    private CommentDaoJpa commentDaoJpa;

    @Test
    void shouldFindCommentById() {
        Comment comment = commentDaoJpa.findById(1L).orElse(null);
        assertThat(comment).isNotNull();
        assertThat(comment.getDescription()).isEqualTo("Хорошо");
    }

    @Test
    void shouldFindAllBook() {
        Author author = new Author(1L, "Достаевский Ф. М.");
        Genre genre = new Genre(1L, "Триллер");
        Book book = new Book(1L, "Преступление и наказание", author, genre);
        List<Comment> comments = commentDaoJpa.findAll(book);
        assertThat(comments.size()).isEqualTo(3);
    }

    @Test
    void shouldInsertBook() {
        Author author = new Author(1L, "Достаевский Ф. М.");
        Genre genre = new Genre(1L, "Триллер");
        Book book = new Book(1L, "Преступление и наказание", author, genre);
        Comment expectedComment = new Comment(0L, "Круто", book);
        expectedComment = commentDaoJpa.save(expectedComment);

        Comment actualComment = commentDaoJpa.findById(expectedComment.getId()).orElse(null);
        assertThat(actualComment).isNotNull();
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(expectedComment);
        commentDaoJpa.delete(expectedComment);
    }

    @Test
    void shouldDeleteBook() {
        Author author = new Author(1L, "Достаевский Ф. М.");
        Genre genre = new Genre(1L, "Триллер");
        Book book = new Book(1L, "Преступление и наказание", author, genre);
        Comment expectedComment = new Comment(0L, "Круто, но скучно", book);
        expectedComment = commentDaoJpa.save(expectedComment);
        commentDaoJpa.delete(expectedComment);
        assertThat(commentDaoJpa.findById(5L).orElse(null)).isNull();
    }

}
