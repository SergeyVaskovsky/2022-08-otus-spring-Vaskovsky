package ru.otus.homework10.mapping;

import lombok.Value;

@Value
public class BookDto {
    long id;
    String name;
    long authorId;
    String authorName;
    long genreId;
    String genreName;
}
