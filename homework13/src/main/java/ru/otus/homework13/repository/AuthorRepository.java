package ru.otus.homework13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework13.model.Author;

import java.util.List;
import java.util.Optional;


public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findAll();

    Optional<Author> findById(long id);
}
