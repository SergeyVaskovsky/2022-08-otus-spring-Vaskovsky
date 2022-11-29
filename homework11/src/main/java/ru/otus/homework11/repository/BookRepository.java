package ru.otus.homework11.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework11.model.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {

    Mono<Book> save(Book book);

    Mono<Book> findById(String id);

    Flux<Book> findAll();
}
