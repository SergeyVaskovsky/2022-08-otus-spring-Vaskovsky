package ru.otus.homework14.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import ru.otus.homework14.model.mongo.Comment;
import ru.otus.homework14.repository.CommentRepository;
import ru.otus.homework14.service.BookIdsService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomCommentWriter implements ItemWriter<Comment> {

    private final CommentRepository commentRepository;
    private final BookIdsService bookIdsService;

    @Override
    public void write(List<? extends Comment> list) {
        for (Comment comment : list) {
            ru.otus.homework14.model.rdb.Comment b =
                    new ru.otus.homework14.model.rdb.Comment(
                            comment.getDescription(),
                            bookIdsService.getIds().get(comment.getBook().getId()));
            commentRepository.save(b);
        }
    }
}
