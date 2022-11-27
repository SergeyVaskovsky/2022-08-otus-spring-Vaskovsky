package ru.otus.homework11.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.homework11.model.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CommentRepositoryJpaTest {

    @Autowired
    private TestEntityManager em;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    void shouldFindCommentById() {
        Comment comment = commentRepository.findById(1L).orElse(null);
        assertThat(comment).isNotNull();
        assertThat(comment.getDescription()).isEqualTo("Хорошо");
    }

    @Test
    void shouldFindAllCommentByBook() {
        List<Comment> expectedComments = List.of(
                em.find(Comment.class, 1L),
                em.find(Comment.class, 2L),
                em.find(Comment.class, 3L)
        );
        List<Comment> comments = commentRepository.findAllByBookId(1L);
        assertThat(comments.size()).isEqualTo(3);
        assertThat(comments).containsExactlyInAnyOrderElementsOf(expectedComments);
    }
}
