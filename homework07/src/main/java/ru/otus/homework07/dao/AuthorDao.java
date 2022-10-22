package ru.otus.homework07.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework07.model.Author;

import java.util.List;
import java.util.Optional;


public interface AuthorDao extends JpaRepository<Author, Long> {
    List<Author> findAll();

    Optional<Author> findById(long id);
}
