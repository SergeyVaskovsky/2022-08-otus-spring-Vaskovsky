package ru.otus.homework14.service;

import org.springframework.stereotype.Service;
import ru.otus.homework14.model.mongo.Author;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthorService {
    private final Map<String, Long> ids = new HashMap<>();
    private long id = 1;

    public ru.otus.homework14.model.rdb.Author doId(Author author) {
        ids.put(author.getId(), id);
        return new ru.otus.homework14.model.rdb.Author(id++, author.getName());
    }

    public Map<String, Long> getIds() {
        return ids;
    }
}
