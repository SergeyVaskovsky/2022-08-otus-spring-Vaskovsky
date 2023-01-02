package ru.otus.homework13.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.homework13.model.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book save(@Param("book") Book book);

    @EntityGraph(attributePaths = {"author", "genre"})
    Book findById(long id);

    @EntityGraph(attributePaths = {"author", "genre"})
    List<Book> findAll();

    void delete(Book book);

}
