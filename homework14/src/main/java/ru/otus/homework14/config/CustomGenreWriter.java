package ru.otus.homework14.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import ru.otus.homework14.model.mongo.Genre;
import ru.otus.homework14.repository.GenreRepository;
import ru.otus.homework14.service.GenreIdsService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomGenreWriter implements ItemWriter<Genre> {

    private final GenreRepository genreRepository;
    private final GenreIdsService genreIdsService;

    @Override
    public void write(List<? extends Genre> list) {
        for (Genre genre : list) {
            ru.otus.homework14.model.rdb.Genre g = new ru.otus.homework14.model.rdb.Genre(genre.getName());
            ru.otus.homework14.model.rdb.Genre savedGenre = genreRepository.save(g);
            genreIdsService.getIds().put(genre.getId(), savedGenre.getId());
        }
    }
}
