package ru.otus.homework10.mapping;

import lombok.Value;
import ru.otus.homework10.model.Book;

@Value
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
