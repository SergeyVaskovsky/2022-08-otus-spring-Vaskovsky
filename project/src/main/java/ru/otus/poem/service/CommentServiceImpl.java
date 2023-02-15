package ru.otus.poem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.poem.exception.CommentNotFoundException;
import ru.otus.poem.model.Comment;
import ru.otus.poem.model.dto.CommentDto;
import ru.otus.poem.repository.CommentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public CommentDto addNewComment(CommentDto commentDto) {
        return CommentDto.toDto(commentRepository.save(CommentDto.toEntity(commentDto)));
    }

    @Override
    public CommentDto getById(Long id) {
        Comment comment = commentRepository
                .findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found by id = " + id));
        return CommentDto.toDto(comment);
    }

    @Override
    public CommentDto updateComment(Long id, CommentDto commentDto) {
        CommentDto commentDtoToSave = new CommentDto(
                id,
                commentDto.getText(),
                commentDto.getUser(),
                commentDto.getPoem(),
                commentDto.getRootComment(),
                commentDto.getPublishTime(),
                commentDto.isModerated()
        );
        return CommentDto.toDto(commentRepository.save(CommentDto.toEntity(commentDtoToSave)));
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public List<CommentDto> getAllByPoemId(Long id) {
        return commentRepository
                .findAllByPoemId(id)
                .stream()
                .map(CommentDto::toDto)
                .collect(Collectors.toList());
    }
}
