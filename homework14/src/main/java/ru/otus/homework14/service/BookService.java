package ru.otus.homework14.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework14.model.mongo.Book;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookService {
    private final Map<String, Long> ids = new HashMap<>();
    private final AuthorService authorService;
    private final GenreService genreService;
    private long id = 1;

    public ru.otus.homework14.model.rdb.Book doId(Book book) {
        ids.put(book.getId(), id);
        return new ru.otus.homework14.model.rdb.Book(
                id++,
                book.getName(),
                authorService.getIds().get(book.getAuthor().getId()),
                genreService.getIds().get(book.getGenre().getId())
        );
    }

    public Map<String, Long> getIds() {
        return ids;
    }
}
