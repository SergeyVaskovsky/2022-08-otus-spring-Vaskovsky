package ru.otus.homework18.mapping;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import ru.otus.homework18.model.Book;

@Value
@Slf4j
public class BookDto {

    long id;
    String name;
    long authorId;
    String authorName;
    long genreId;
    String genreName;

    public static BookDto toDto(Book b) {
        return new BookDto(
                b.getId(),
                b.getName(),
                b.getAuthor().getId(),
                b.getAuthor().getName(),
                b.getGenre().getId(),
                b.getGenre().getName()
        );
    }
}
