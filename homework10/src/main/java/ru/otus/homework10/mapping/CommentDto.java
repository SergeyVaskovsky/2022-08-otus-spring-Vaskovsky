package ru.otus.homework10.mapping;

import lombok.Value;

@Value
public class CommentDto {
    Long id;
    String description;
    Long bookId;
}
