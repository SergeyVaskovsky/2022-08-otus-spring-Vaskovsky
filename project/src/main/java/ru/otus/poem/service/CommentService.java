package ru.otus.poem.service;

import ru.otus.poem.model.dto.CommentDto;

public interface CommentService {
    CommentDto addNewComment(CommentDto commentDto);
}
