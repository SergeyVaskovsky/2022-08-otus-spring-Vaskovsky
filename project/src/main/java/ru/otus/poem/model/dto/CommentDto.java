package ru.otus.poem.model.dto;

import lombok.Value;
import ru.otus.poem.model.Comment;

import java.time.LocalDateTime;

@Value
public class CommentDto {
    long id;
    String text;
    UserDto user;
    PoemDto poem;
    CommentDto rootComment;
    LocalDateTime publishTime;
    boolean moderated;

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                UserDto.toDto(comment.getUser()),
                PoemDto.toDto(comment.getPoem()),
                comment.getRootComment() == null ? null : CommentDto.toDto(comment.getRootComment()),
                comment.getPublishTime(),
                comment.isModerated()
        );
    }

    public static Comment toEntity(CommentDto commentDto) {
        return new Comment(
                commentDto.getId(),
                commentDto.getText(),
                UserDto.toEntity(commentDto.getUser()),
                PoemDto.toEntity(commentDto.getPoem()),
                commentDto.getRootComment() == null ? null : CommentDto.toEntity(commentDto.getRootComment()),
                commentDto.getPublishTime(),
                commentDto.isModerated()
        );
    }
}
