package ru.otus.homework14.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework14.model.rdb.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
