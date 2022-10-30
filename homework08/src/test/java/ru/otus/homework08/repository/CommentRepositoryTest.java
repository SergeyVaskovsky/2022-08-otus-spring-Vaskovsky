package ru.otus.homework08.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.homework08.model.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void shouldFindCommentById() {
        Comment comment = commentRepository.findById("1").orElse(null);
        assertThat(comment).isNotNull();
        assertThat(comment.getDescription()).isEqualTo("Хорошо");
    }

    @Test
    void shouldFindAllCommentByBook() {
        List<Comment> comments = commentRepository.findAllByBookId("1");
        assertThat(comments.size()).isEqualTo(3);
    }

    @Test
    void shouldDeleteAllCommentByBook() {
        Query query = new Query();
        query.addCriteria(Criteria.where("bookId").is("1"));
        List<Comment> comments = mongoTemplate.find(query, Comment.class);
        assertThat(mongoTemplate.count(query, Comment.class)).isEqualTo(3);
        commentRepository.deleteAllByBookId("1");
        assertThat(mongoTemplate.count(query, Comment.class)).isEqualTo(0);
        mongoTemplate.insert(comments, Comment.class);
    }
}
