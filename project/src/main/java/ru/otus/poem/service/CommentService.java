package ru.otus.poem.service;

import ru.otus.poem.model.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto addNewComment(CommentDto commentDto);

    CommentDto getById(Long id);

    CommentDto updateComment(Long id, CommentDto commentDto);

    void deleteById(Long id);

    List<CommentDto> getAllByPoemId(Long id);
}
