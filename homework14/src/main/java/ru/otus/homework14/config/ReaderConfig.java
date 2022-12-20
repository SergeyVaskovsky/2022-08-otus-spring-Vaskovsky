package ru.otus.homework14.config;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.homework14.model.mongo.Author;
import ru.otus.homework14.model.mongo.Book;
import ru.otus.homework14.model.mongo.Comment;
import ru.otus.homework14.model.mongo.Genre;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ReaderConfig {

    @StepScope
    @Bean
    public MongoItemReader<Author> readerAuthor(MongoTemplate mongoTemplate) {
        Map<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("_id", Sort.Direction.ASC);
        return new MongoItemReaderBuilder<Author>()
                .name("authorItemReader")
                .template(mongoTemplate)
                .collection("author")
                .targetType(Author.class)
                .query(new Query())
                .sorts(sorts)
                .build();
    }

    @StepScope
    @Bean
    public MongoItemReader<Genre> readerGenre(MongoTemplate mongoTemplate) {
        Map<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("_id", Sort.Direction.ASC);
        return new MongoItemReaderBuilder<Genre>()
                .name("genreItemReader")
                .template(mongoTemplate)
                .collection("genre")
                .targetType(Genre.class)
                .query(new Query())
                .sorts(sorts)
                .build();
    }

    @StepScope
    @Bean
    public MongoItemReader<Book> readerBook(MongoTemplate mongoTemplate) {
        Map<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("_id", Sort.Direction.ASC);
        return new MongoItemReaderBuilder<Book>()
                .name("bookItemReader")
                .template(mongoTemplate)
                .collection("book")
                .targetType(Book.class)
                .query(new Query())
                .sorts(sorts)
                .build();
    }

    @StepScope
    @Bean
    public MongoItemReader<Comment> readerComment(MongoTemplate mongoTemplate) {
        Map<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("_id", Sort.Direction.ASC);
        return new MongoItemReaderBuilder<Comment>()
                .name("commentItemReader")
                .template(mongoTemplate)
                .collection("comment")
                .targetType(Comment.class)
                .query(new Query())
                .sorts(sorts)
                .build();
    }
}
