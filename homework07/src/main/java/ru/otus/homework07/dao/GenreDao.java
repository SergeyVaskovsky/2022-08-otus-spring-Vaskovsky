package ru.otus.homework07.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework07.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao extends JpaRepository<Genre, Long> {
    List<Genre> findAll();

    Optional<Genre> findById(long id);
}
