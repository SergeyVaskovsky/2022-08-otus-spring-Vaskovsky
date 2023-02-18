package ru.otus.poem.mapping;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.otus.poem.model.Comment;
import ru.otus.poem.model.Poem;
import ru.otus.poem.model.User;
import ru.otus.poem.model.dto.CommentDto;
import ru.otus.poem.service.CommentService;
import ru.otus.poem.service.PoemService;
import ru.otus.poem.service.UserService;

@Service
@RequiredArgsConstructor
public class CommentDtoToComment implements Converter<CommentDto, Comment> {

    private final UserService userService;
    private final PoemService poemService;
    private final CommentService commentService;
    private final ConversionService conversionService;

    @Override
    public Comment convert(CommentDto source) {
        return new Comment(
                source.getId(),
                source.getText(),
                conversionService.convert(userService.getById(source.getUserId()), User.class),
                conversionService.convert(poemService.getById(source.getPoemId()), Poem.class),
                source.getRootCommentId() == null ? null : conversionService.convert(commentService.getById(source.getRootCommentId()), Comment.class),
                source.getPublishTime(),
                source.isModerated()
        );
    }
}
