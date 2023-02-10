package ru.otus.homework18.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.otus.homework18.exception.AuthorNotFoundException;
import ru.otus.homework18.model.Author;
import ru.otus.homework18.repository.AuthorRepository;
import ru.otus.homework18.repository.CacheRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    @Qualifier("authorCacheRepositoryImpl")
    private final CacheRepository cacheRepository;
    private final RandomTimeoutService randomTimeoutService;

    @HystrixCommand(fallbackMethod = "buildFallbackAuthors")
    public List<Author> getAll() {
        randomTimeoutService.sleepRandomTimeout();
        List<Author> authors = authorRepository.findAll();
        cacheRepository.deleteAll();
        authors.forEach(a -> cacheRepository.save(a.getId(), a));
        return authors;
    }

    public List<Author> buildFallbackAuthors() {

        /**
         * Код удаления значения из кэша нужен исключительно для того,
         * чтобы продемонтрировать работу метода.
         */
        cacheRepository.delete(4L);

        return cacheRepository.findAll();
    }

    public Author getById(long id) {
        return authorRepository
                .findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(String.format("Author not found by id = %s", id)));
    }
}
