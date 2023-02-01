package ru.otus.homework18.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import ru.otus.homework18.model.Book;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {


    @EntityGraph(attributePaths = {"author", "genre"})
    Optional<Book> findWithAuthorWithGenreById(long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Book> findById(long id);

    @EntityGraph(attributePaths = {"author", "genre"})
    List<Book> findAll();

}
