package ru.otus.homework10.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework10.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book save(Book book);

    @EntityGraph(attributePaths = {"author", "genre"})
    Optional<Book> findById(long id);

    @EntityGraph(attributePaths = {"author", "genre"})
    List<Book> findAll();

    void delete(Book book);
}
