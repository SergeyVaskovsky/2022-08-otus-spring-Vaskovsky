package ru.otus.homework14.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import ru.otus.homework14.model.mongo.Book;
import ru.otus.homework14.repository.BookRepository;
import ru.otus.homework14.service.AuthorIdsService;
import ru.otus.homework14.service.BookIdsService;
import ru.otus.homework14.service.GenreIdsService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomBookWriter implements ItemWriter<Book> {

    private final BookRepository bookRepository;
    private final BookIdsService bookIdsService;
    private final AuthorIdsService authorIdsService;
    private final GenreIdsService genreIdsService;

    @Override
    public void write(List<? extends Book> list) {
        for (Book book : list) {
            ru.otus.homework14.model.rdb.Book b =
                    new ru.otus.homework14.model.rdb.Book(
                            book.getName(),
                            authorIdsService.getIds().get(book.getAuthor().getId()),
                            genreIdsService.getIds().get(book.getGenre().getId()));
            ru.otus.homework14.model.rdb.Book savedBook = bookRepository.save(b);
            bookIdsService.getIds().put(book.getId(), savedBook.getId());
        }
    }
}
