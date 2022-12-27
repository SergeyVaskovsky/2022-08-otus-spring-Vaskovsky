package ru.otus.homework14.config;

import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.homework14.model.mongo.MongoAuthor;
import ru.otus.homework14.model.mongo.MongoBook;
import ru.otus.homework14.model.mongo.MongoComment;
import ru.otus.homework14.model.mongo.MongoGenre;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ReaderConfig {

    @Bean
    public MongoItemReader<MongoAuthor> readerAuthor(MongoTemplate mongoTemplate) {
        Map<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("_id", Sort.Direction.ASC);
        return new MongoItemReaderBuilder<MongoAuthor>()
                .name("authorItemReader")
                .template(mongoTemplate)
                .collection("author")
                .targetType(MongoAuthor.class)
                .query(new Query())
                .sorts(sorts)
                .build();
    }

    @Bean
    public MongoItemReader<MongoGenre> readerGenre(MongoTemplate mongoTemplate) {
        Map<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("_id", Sort.Direction.ASC);
        return new MongoItemReaderBuilder<MongoGenre>()
                .name("genreItemReader")
                .template(mongoTemplate)
                .collection("genre")
                .targetType(MongoGenre.class)
                .query(new Query())
                .sorts(sorts)
                .build();
    }

    @Bean
    public MongoItemReader<MongoBook> readerBook(MongoTemplate mongoTemplate) {
        Map<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("_id", Sort.Direction.ASC);
        return new MongoItemReaderBuilder<MongoBook>()
                .name("bookItemReader")
                .template(mongoTemplate)
                .collection("book")
                .targetType(MongoBook.class)
                .query(new Query())
                .sorts(sorts)
                .build();
    }

    @Bean
    public MongoItemReader<MongoComment> readerComment(MongoTemplate mongoTemplate) {
        Map<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("_id", Sort.Direction.ASC);
        return new MongoItemReaderBuilder<MongoComment>()
                .name("commentItemReader")
                .template(mongoTemplate)
                .collection("comment")
                .targetType(MongoComment.class)
                .query(new Query())
                .sorts(sorts)
                .build();
    }
}
