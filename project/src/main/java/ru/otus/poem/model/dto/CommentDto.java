package ru.otus.poem.model.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class CommentDto {
    long id;
    String text;
    Long userId;
    Long poemId;
    Long rootCommentId;
    LocalDateTime publishTime;
    boolean moderated;
}
