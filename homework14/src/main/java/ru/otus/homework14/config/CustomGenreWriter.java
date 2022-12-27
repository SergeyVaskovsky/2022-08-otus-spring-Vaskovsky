package ru.otus.homework14.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import ru.otus.homework14.model.mongo.MongoGenre;
import ru.otus.homework14.model.rdb.RdbGenre;
import ru.otus.homework14.repository.GenreRepository;
import ru.otus.homework14.service.GenreIdsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomGenreWriter implements ItemWriter<MongoGenre> {

    private final GenreRepository genreRepository;
    private final GenreIdsService genreIdsService;

    @Override
    public void write(List<? extends MongoGenre> list) {
        Map<MongoGenre, RdbGenre> map = new HashMap<>();
        for (MongoGenre mongoGenre : list) {
            RdbGenre rdbGenre = new RdbGenre(mongoGenre.getName());
            map.put(mongoGenre, rdbGenre);
        }
        if (!map.isEmpty()) {
            genreRepository.saveAll(map.values());
            map
                    .forEach((key, value) -> genreIdsService.putRdbId(key.getId(), value.getId()));
        }
    }
}
