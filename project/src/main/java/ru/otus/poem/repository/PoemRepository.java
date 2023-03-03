package ru.otus.poem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.poem.model.Poem;

import java.util.List;

public interface PoemRepository extends JpaRepository<Poem, Long> {
    List<Poem> findByPublishTimeIsNotNull();
}
