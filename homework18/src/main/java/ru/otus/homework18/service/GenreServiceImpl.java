package ru.otus.homework18.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.otus.homework18.exception.GenreNotFoundException;
import ru.otus.homework18.model.Genre;
import ru.otus.homework18.repository.CacheRepository;
import ru.otus.homework18.repository.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    @Qualifier("genreCacheRepositoryImpl")
    private final CacheRepository cacheRepository;
    private final RandomTimeoutService randomTimeoutService;

    @HystrixCommand(fallbackMethod = "buildFallbackGenres")
    public List<Genre> getAll() {
        randomTimeoutService.sleepRandomTimeout();
        List<Genre> genres = genreRepository.findAll();
        cacheRepository.deleteAll();
        genres.forEach(g -> cacheRepository.save(g.getId(), g));
        return genres;
    }

    public List<Genre> buildFallbackGenres() {

        /**
         * Код удаления значения из кэша нужен исключительно для того,
         * чтобы продемонтрировать работу метода.
         */
        cacheRepository.delete(3L);

        return cacheRepository.findAll();
    }

    public Genre getById(long id) {
        return genreRepository
                .findById(id)
                .orElseThrow(() -> new GenreNotFoundException(String.format("Genre not found by id = %s", id)));
    }

}
