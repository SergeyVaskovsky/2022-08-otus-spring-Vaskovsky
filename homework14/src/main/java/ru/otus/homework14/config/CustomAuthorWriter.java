package ru.otus.homework14.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import ru.otus.homework14.model.mongo.Author;
import ru.otus.homework14.repository.AuthorRepository;
import ru.otus.homework14.service.AuthorIdsService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomAuthorWriter implements ItemWriter<Author> {
    private final AuthorRepository authorRepository;
    private final AuthorIdsService authorIdsService;

    @Override
    public void write(List<? extends Author> list) {
        for (Author author : list) {
            ru.otus.homework14.model.rdb.Author a = new ru.otus.homework14.model.rdb.Author(author.getName());
            ru.otus.homework14.model.rdb.Author savedAuthor = authorRepository.save(a);
            authorIdsService.getIds().put(author.getId(), savedAuthor.getId());
        }
    }
}

