package ru.otus.homework14.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.homework14.model.rdb.Author;
import ru.otus.homework14.model.rdb.Book;
import ru.otus.homework14.model.rdb.Comment;
import ru.otus.homework14.model.rdb.Genre;

import javax.persistence.EntityManagerFactory;

@Configuration
@RequiredArgsConstructor
public class WriterConfig {

    @StepScope
    @Bean
    public JpaItemWriter<Author> writerAuthor(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<Author> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }

    @StepScope
    @Bean
    public JpaItemWriter<Genre> writerGenre(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<Genre> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }

    @StepScope
    @Bean
    public JpaItemWriter<Book> writerBook(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<Book> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }

    @StepScope
    @Bean
    public JpaItemWriter<Comment> writerComment(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<Comment> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }
}
