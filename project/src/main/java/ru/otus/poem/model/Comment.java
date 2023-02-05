package ru.otus.poem.model;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class Comment {
    long id;
    String text;
    User user;
    Poem poem;
    Comment rootComment;
    LocalDateTime publishTime;
    boolean moderated;
}
