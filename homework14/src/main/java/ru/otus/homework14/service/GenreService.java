package ru.otus.homework14.service;

import org.springframework.stereotype.Service;
import ru.otus.homework14.model.mongo.Genre;

import java.util.HashMap;
import java.util.Map;

@Service
public class GenreService {
    private final Map<String, Long> ids = new HashMap<>();
    private long id = 1;

    public ru.otus.homework14.model.rdb.Genre doId(Genre genre) {
        ids.put(genre.getId(), id);
        return new ru.otus.homework14.model.rdb.Genre(id++, genre.getName());
    }

    public Map<String, Long> getIds() {
        return ids;
    }
}
