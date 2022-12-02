package ru.otus.homework11.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import ru.otus.homework11.changelogs.ApplicationStartup;
import ru.otus.homework11.model.Comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
public class CommentRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private CommentRepository commentRepository;

    @BeforeEach
    void createData() {
        ApplicationStartup applicationStartup = new ApplicationStartup(
                bookRepository, authorRepository, genreRepository, commentRepository
        );
        applicationStartup.start();
    }

    @Test
    void shouldFindAllCommentByBook() {
        Flux<Comment> result = commentRepository.findAllByBookId("1");
        StepVerifier
                .create(result)
                .assertNext(comment -> {
                    assertEquals("Хорошо", comment.getDescription());
                    assertNotNull(comment.getId());
                })
                .assertNext(comment -> {
                    assertEquals("Плохо", comment.getDescription());
                    assertNotNull(comment.getId());
                })
                .assertNext(comment -> {
                    assertEquals("Ни рыба не мясо", comment.getDescription());
                    assertNotNull(comment.getId());
                })
                .expectComplete()
                .verify();
    }
}
