package ru.otus.homework11.mapping;

import lombok.Value;
import ru.otus.homework11.model.Book;

@Value
public class BookDto {

    String id;
    String name;
    String authorId;
    String authorName;
    String genreId;
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
