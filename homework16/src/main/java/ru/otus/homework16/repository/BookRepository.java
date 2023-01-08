package ru.otus.homework16.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.homework16.model.Book;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "books", excerptProjection = CustomBook.class)
public interface BookRepository extends JpaRepository<Book, Long> {

    Book save(Book book);

    @EntityGraph(attributePaths = {"author", "genre"})
    Optional<Book> findById(long id);

    @EntityGraph(attributePaths = {"author", "genre"})
    List<Book> findAll();

}
