package ru.otus.poem.mapping;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.otus.poem.model.Comment;
import ru.otus.poem.model.dto.CommentDto;

@Service
public class CommentToCommentDto implements Converter<Comment, CommentDto> {

    @Override
    public CommentDto convert(Comment source) {
        return new CommentDto(
                source.getId(),
                source.getText(),
                source.getUser().getId(),
                source.getUser().getName(),
                source.getPoem().getId(),
                source.getRootComment() == null ? null : source.getRootComment().getId(),
                source.getPublishTime(),
                source.isModerated()
        );
    }
}
