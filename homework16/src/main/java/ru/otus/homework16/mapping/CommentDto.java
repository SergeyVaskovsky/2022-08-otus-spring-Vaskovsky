package ru.otus.homework16.mapping;

import lombok.Value;
import ru.otus.homework16.model.Comment;

@Value
public class CommentDto {
    Long id;
    String description;
    Long bookId;

    public static CommentDto toDto(Comment c) {
        return new CommentDto(c.getId(), c.getDescription(), -1L/*, c.getBook().getId()*/);
    }
}
