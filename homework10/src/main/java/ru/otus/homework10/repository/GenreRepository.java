package ru.otus.homework10.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework10.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    List<Genre> findAll();
    Optional<Genre> findById(long id);
}
