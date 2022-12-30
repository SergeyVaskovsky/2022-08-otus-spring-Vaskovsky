package ru.otus.homework14.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.homework14.model.mongo.MongoAuthor;
import ru.otus.homework14.model.mongo.MongoBook;
import ru.otus.homework14.model.mongo.MongoComment;
import ru.otus.homework14.model.mongo.MongoGenre;

@Configuration
@RequiredArgsConstructor
public class JobConfig {

    public static final String IMPORT_JOB_NAME = "importJob";
    public static final String STEP_AUTHOR_NAME = "stepAuthor";
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
        return jobBuilderFactory.get(IMPORT_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(transformAuthorsStep)
                .next(transformGenresStep)
                .next(transformBooksStep)
                .next(transformCommentsStep)
                .end()
                .build();
    }

    @Bean
    public Step transformAuthorsStep(MongoItemReader<MongoAuthor> reader,
                                     CustomAuthorWriter writer) {
        return stepBuilderFactory.get(STEP_AUTHOR_NAME)
                .<MongoAuthor, MongoAuthor>chunk(CHUNK_SIZE)
                .reader(reader)
                .writer(writer)
                .build();
    }

    @Bean
    public Step transformGenresStep(
            MongoItemReader<MongoGenre> reader,
            CustomGenreWriter writer
    ) {
        return stepBuilderFactory.get(STEP_GENRE_NAME)
                .<MongoGenre, MongoGenre>chunk(CHUNK_SIZE)
                .reader(reader)
                .writer(writer)
                .build();
    }

    @Bean
    public Step transformBooksStep(
            MongoItemReader<MongoBook> reader,
            CustomBookWriter writer) {
        return stepBuilderFactory.get(STEP_BOOK_NAME)
                .<MongoBook, MongoBook>chunk(CHUNK_SIZE)
                .reader(reader)
                .writer(writer)
                .build();
    }

    @Bean
    public Step transformCommentsStep(
            MongoItemReader<MongoComment> reader,
            CustomCommentWriter writer) {
        return stepBuilderFactory.get(STEP_COMMENT_NAME)
                .<MongoComment, MongoComment>chunk(CHUNK_SIZE)
                .reader(reader)
                .writer(writer)
                .build();
    }
}
