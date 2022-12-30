package ru.otus.homework14.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import ru.otus.homework14.model.mongo.MongoAuthor;
import ru.otus.homework14.model.rdb.RdbAuthor;
import ru.otus.homework14.repository.AuthorRepository;
import ru.otus.homework14.service.AuthorIdsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomAuthorWriter implements ItemWriter<MongoAuthor> {
    private final AuthorRepository authorRepository;
    private final AuthorIdsService authorIdsService;

    @Override
    public void write(List<? extends MongoAuthor> list) {
        Map<MongoAuthor, RdbAuthor> map = new HashMap<>();
        for (MongoAuthor mongoAuthor : list) {
            RdbAuthor rdbAuthor = new RdbAuthor(mongoAuthor.getName());
            map.put(mongoAuthor, rdbAuthor);
        }
        if (!map.isEmpty()) {
            authorRepository.saveAll(map.values());
            map
                    .forEach((key, value) -> authorIdsService.putRdbId(key.getId(), value.getId()));
        }
    }
}



