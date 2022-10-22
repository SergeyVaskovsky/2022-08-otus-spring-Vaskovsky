package ru.otus.homework08.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.homework08.model.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class CommentDaoJpaTest {

    @Autowired
    private CommentDao commentDao;

    @Test
    void shouldFindCommentById() {
        Comment comment = commentDao.findById(1L).orElse(null);
        assertThat(comment).isNotNull();
        assertThat(comment.getDescription()).isEqualTo("Хорошо");
    }

    @Test
    void shouldFindAllCommentByBook() {
        List<Comment> comments = commentDao.findAllByBookId(1L);
        assertThat(comments.size()).isEqualTo(3);
    }
}
