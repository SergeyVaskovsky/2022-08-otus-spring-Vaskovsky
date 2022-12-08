package ru.otus.homework11.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import ru.otus.homework11.changelogs.ApplicationStartup;
import ru.otus.homework11.changelogs.DbInitializer;
import ru.otus.homework11.model.Comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@AutoConfigureDataMongo
@SpringBootTest
public class CommentRepositoryTest {
    @Autowired
    private DbInitializer dbInitializer;

    @Autowired
    private CommentRepository commentRepository;

    @BeforeEach
    void createData() {
        dbInitializer.initDb();
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
