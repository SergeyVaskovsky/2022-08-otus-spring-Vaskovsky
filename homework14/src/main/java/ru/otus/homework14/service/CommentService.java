package ru.otus.homework14.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework14.model.mongo.Comment;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final Map<String, Long> ids = new HashMap<>();
    private final BookService bookService;
    private long id = 1;

    public ru.otus.homework14.model.rdb.Comment doId(Comment comment) {
        ids.put(comment.getId(), id);
        return new ru.otus.homework14.model.rdb.Comment(
                id++,
                comment.getDescription(),
                bookService.getIds().get(comment.getBook().getId())
        );

    }
}
