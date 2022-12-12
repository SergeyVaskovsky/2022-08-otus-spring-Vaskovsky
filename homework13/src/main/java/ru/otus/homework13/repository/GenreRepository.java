package ru.otus.homework13.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import ru.otus.homework13.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    @PostFilter("hasPermission(filterObject, 'READ')")
    List<Genre> findAll();

    @PostAuthorize("hasPermission(returnObject, 'READ')")
    Optional<Genre> findById(long id);
}
