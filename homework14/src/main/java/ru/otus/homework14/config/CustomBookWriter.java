package ru.otus.homework14.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import ru.otus.homework14.model.mongo.MongoBook;
import ru.otus.homework14.model.rdb.RdbBook;
import ru.otus.homework14.repository.BookRepository;
import ru.otus.homework14.service.AuthorIdsService;
import ru.otus.homework14.service.BookIdsService;
import ru.otus.homework14.service.GenreIdsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomBookWriter implements ItemWriter<MongoBook> {

    private final BookRepository bookRepository;
    private final BookIdsService bookIdsService;
    private final AuthorIdsService authorIdsService;
    private final GenreIdsService genreIdsService;

    @Override
    public void write(List<? extends MongoBook> list) {
        Map<MongoBook, RdbBook> map = new HashMap<>();
        for (MongoBook mongoBook : list) {
            RdbBook rdbBook =
                    new RdbBook(
                            mongoBook.getName(),
                            authorIdsService.getRdbId(mongoBook.getMongoAuthor().getId()),
                            genreIdsService.getRdbId(mongoBook.getMongoGenre().getId()));
            map.put(mongoBook, rdbBook);
        }
        if (!map.isEmpty()) {
            bookRepository.saveAll(map.values());
            map
                    .forEach((key, value) -> bookIdsService.putRdbId(key.getId(), value.getId()));
        }
    }
}
