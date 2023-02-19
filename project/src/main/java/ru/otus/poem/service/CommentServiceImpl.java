package ru.otus.poem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.poem.exception.CommentNotFoundException;
import ru.otus.poem.integration.ModeratorActivityGateway;
import ru.otus.poem.model.Comment;
import ru.otus.poem.model.dto.CommentDto;
import ru.otus.poem.repository.CommentRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ModeratorActivityGateway moderatorActivityGateway;
    private final ConversionService conversionService;
    @Override
    public CommentDto addNewComment(CommentDto commentDto) {
        Comment comment = conversionService.convert(commentDto, Comment.class);
        Comment savedComment = commentRepository.save(comment);
        moderatorActivityGateway.processActivity(savedComment);
        return conversionService.convert(savedComment, CommentDto.class);
    }

    @Override
    public CommentDto getById(Long id) {
        Comment comment = commentRepository
                .findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found by id = " + id));
        return conversionService.convert(comment, CommentDto.class);
    }

    @Override
    @Transactional
    public CommentDto updateComment(Long id, CommentDto commentDto) {
        CommentDto commentDtoToSave = new CommentDto(
                id,
                commentDto.getText(),
                commentDto.getUserId(),
                commentDto.getPoemId(),
                commentDto.getRootCommentId(),
                commentDto.getPublishTime(),
                commentDto.isModerated()
        );
        Comment commentToSave = conversionService.convert(commentDtoToSave, Comment.class);
        return conversionService.convert(commentRepository.save(commentToSave), CommentDto.class);
    }

    @Override
    public void deleteById(Long id) {
        try {
            commentRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.error("Comment with id " + id + " not exists", e);
        }
    }

    @Override
    public List<CommentDto> getAllByPoemId(Long id) {
        return commentRepository
                .findAllByPoemId(id)
                .stream()
                .map(it -> conversionService.convert(it, CommentDto.class))
                .collect(Collectors.toList());
    }
}
