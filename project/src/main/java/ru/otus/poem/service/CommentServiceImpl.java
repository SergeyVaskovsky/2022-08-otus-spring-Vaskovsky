package ru.otus.poem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.poem.model.dto.CommentDto;
import ru.otus.poem.repository.CommentRepository;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public CommentDto addNewComment(CommentDto commentDto) {
        return CommentDto.toDto(commentRepository.save(CommentDto.toEntity(commentDto)));
    }
}
