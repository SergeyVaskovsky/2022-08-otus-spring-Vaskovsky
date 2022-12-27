package ru.otus.homework14.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import ru.otus.homework14.model.mongo.MongoComment;
import ru.otus.homework14.model.rdb.RdbComment;
import ru.otus.homework14.repository.CommentRepository;
import ru.otus.homework14.service.BookIdsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomCommentWriter implements ItemWriter<MongoComment> {

    private final CommentRepository commentRepository;
    private final BookIdsService bookIdsService;

    @Override
    public void write(List<? extends MongoComment> list) {
        Map<MongoComment, RdbComment> map = new HashMap<>();
        for (MongoComment mongoComment : list) {
            RdbComment rdbComment =
                    new RdbComment(
                            mongoComment.getDescription(),
                            bookIdsService.getRdbId(mongoComment.getMongoBook().getId()));
            map.put(mongoComment, rdbComment);
        }
        if (!map.isEmpty()) {
            commentRepository.saveAll(map.values());
        }
    }
}
