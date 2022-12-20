package ru.otus.homework14.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.homework14.model.mongo.Author;
import ru.otus.homework14.model.mongo.Book;
import ru.otus.homework14.model.mongo.Comment;
import ru.otus.homework14.model.mongo.Genre;
import ru.otus.homework14.service.AuthorService;
import ru.otus.homework14.service.BookService;
import ru.otus.homework14.service.CommentService;
import ru.otus.homework14.service.GenreService;

@Configuration
@RequiredArgsConstructor
public class ItemProcessorConfig {
    @StepScope
    @Bean
    public ItemProcessor<Author, ru.otus.homework14.model.rdb.Author> processorAuthor(AuthorService authorService) {
        return authorService::doId;
    }

    @StepScope
    @Bean
    public ItemProcessor<Genre, ru.otus.homework14.model.rdb.Genre> processorGenre(GenreService genreService) {
        return genreService::doId;
    }

    @StepScope
    @Bean
    public ItemProcessor<Book, ru.otus.homework14.model.rdb.Book> processorBook(BookService bookService) {
        return bookService::doId;
    }

    @StepScope
    @Bean
    public ItemProcessor<Comment, ru.otus.homework14.model.rdb.Comment> processorComment(CommentService commentService) {
        return commentService::doId;
    }
}
