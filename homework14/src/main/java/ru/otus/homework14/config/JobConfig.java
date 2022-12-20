package ru.otus.homework14.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.homework14.model.mongo.Author;
import ru.otus.homework14.model.mongo.Book;
import ru.otus.homework14.model.mongo.Comment;
import ru.otus.homework14.model.mongo.Genre;

@Configuration
@RequiredArgsConstructor
public class JobConfig {
    public static final String STEP_AUTHOR_NAME = "stepAuthor";
    public static final String IMPORT_USER_JOB_NAME = "importUserJob";
    public static final String STEP_GENRE_NAME = "stepGenre";
    public static final String STEP_BOOK_NAME = "stepBook";
    public static final String STEP_COMMENT_NAME = "stepComment";
    private static final int CHUNK_SIZE = 3;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job importUserJob(
            Step transformAuthorsStep,
            Step transformGenresStep,
            Step transformBooksStep,
            Step transformCommentsStep) {
        return jobBuilderFactory.get(IMPORT_USER_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(transformAuthorsStep)
                .next(transformGenresStep)
                .next(transformBooksStep)
                .next(transformCommentsStep)
                .end()
                .build();
    }

    @Bean
    public Step transformAuthorsStep(
            MongoItemReader<Author> reader,
            JpaItemWriter<ru.otus.homework14.model.rdb.Author> writer,
            ItemProcessor<Author, ru.otus.homework14.model.rdb.Author> itemProcessor) {
        return stepBuilderFactory.get(STEP_AUTHOR_NAME)
                .<Author, ru.otus.homework14.model.rdb.Author>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step transformGenresStep(
            MongoItemReader<Genre> reader,
            JpaItemWriter<ru.otus.homework14.model.rdb.Genre> writer,
            ItemProcessor<Genre, ru.otus.homework14.model.rdb.Genre> itemProcessor) {
        return stepBuilderFactory.get(STEP_GENRE_NAME)
                .<Genre, ru.otus.homework14.model.rdb.Genre>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step transformBooksStep(
            MongoItemReader<Book> reader,
            JpaItemWriter<ru.otus.homework14.model.rdb.Book> writer,
            ItemProcessor<Book, ru.otus.homework14.model.rdb.Book> itemProcessor) {
        return stepBuilderFactory.get(STEP_BOOK_NAME)
                .<Book, ru.otus.homework14.model.rdb.Book>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step transformCommentsStep(
            MongoItemReader<Comment> reader,
            JpaItemWriter<ru.otus.homework14.model.rdb.Comment> writer,
            ItemProcessor<Comment, ru.otus.homework14.model.rdb.Comment> itemProcessor) {
        return stepBuilderFactory.get(STEP_COMMENT_NAME)
                .<Comment, ru.otus.homework14.model.rdb.Comment>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .build();
    }
}
