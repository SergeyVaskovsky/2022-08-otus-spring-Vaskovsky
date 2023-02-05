package ru.otus.poem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.poem.model.Poem;

public interface PoemRepository extends JpaRepository<Poem, Long> {
}
