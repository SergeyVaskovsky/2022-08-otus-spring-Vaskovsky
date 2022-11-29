package ru.otus.homework11.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import ru.otus.homework11.model.Comment;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;

    @Test
    void shouldFindAllCommentByBook() {
        /*List<Comment> expectedComments = List.of(
                em.find(Comment.class, 1L),
                em.find(Comment.class, 2L),
                em.find(Comment.class, 3L)
        );
        List<Comment> comments = commentRepository.findAllByBookId(1L);
        assertThat(comments.size()).isEqualTo(3);
        assertThat(comments).containsExactlyInAnyOrderElementsOf(expectedComments);*/


        Flux<Comment> result = commentRepository.findAllByBookId("1");

        StepVerifier
                .create(result)
                .assertNext(comment -> assertNotNull(comment))
                .assertNext(comments -> assertThat(comments.s))


                .create(personMono)
                .assertNext(person -> assertNotNull(person.getId()))
                .expectComplete()
                .verify();
    }
}
