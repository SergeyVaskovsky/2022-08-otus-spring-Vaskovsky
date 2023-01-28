package ru.otus.homework18.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework18.exception.AuthorNotFoundException;
import ru.otus.homework18.model.Author;
import ru.otus.homework18.repository.AuthorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final RandomTimeoutService randomTimeoutService;

    @HystrixCommand(fallbackMethod = "buildFallbackAuthors")
    public List<Author> getAll() {
        randomTimeoutService.sleepRandomTimeout();
        return authorRepository.findAll();
    }

    public List<Author> buildFallbackAuthors() {
        Author author = new Author(0L, "Нет данных");
        return List.of(author);
    }

    public Author getById(long id) {
        return authorRepository
                .findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(String.format("Author not found by id = %s", id)));
    }
}
