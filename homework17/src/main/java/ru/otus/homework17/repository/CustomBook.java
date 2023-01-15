package ru.otus.homework17.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;
import ru.otus.homework17.model.Book;

@Projection(name = "customBook", types = Book.class)
public interface CustomBook {
    String getName();

    @Value("#{target.author.id}")
    String getAuthorId();

    @Value("#{target.author.name}")
    String getAuthorName();

    @Value("#{target.genre.id}")
    String getGenreId();

    @Value("#{target.genre.name}")
    String getGenreName();
}


