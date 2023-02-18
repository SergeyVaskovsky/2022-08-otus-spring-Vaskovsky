package ru.otus.poem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.poem.model.dto.CommentDto;

@Service
@RequiredArgsConstructor
public class TelegramBotServiceImpl implements TelegramBotService {

    private final CommentService commentService;

    @Override
    public void moderate(String id) {
        CommentDto commentDto = commentService.getById(Long.valueOf(id));
        CommentDto commentDtoToSave = new CommentDto(
                commentDto.getId(),
                commentDto.getText(),
                commentDto.getUser(),
                commentDto.getPoem(),
                commentDto.getRootComment(),
                commentDto.getPublishTime(),
                true
        );
        commentService.updateComment(commentDtoToSave.getId(), commentDtoToSave);
    }

    @Override
    public void delete(String id) {
        commentService.deleteById(Long.valueOf(id));
    }
}
