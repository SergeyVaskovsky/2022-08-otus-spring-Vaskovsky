package ru.otus.homework18.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework18.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
