package ru.otus.homework11.mapping;

import lombok.Value;
import ru.otus.homework11.model.Comment;

@Value
public class CommentDto {
    Long id;
    String description;
    Long bookId;

    public static CommentDto toDto(Comment c) {
        return new CommentDto(c.getId(), c.getDescription(), c.getBook().getId());
    }
}
