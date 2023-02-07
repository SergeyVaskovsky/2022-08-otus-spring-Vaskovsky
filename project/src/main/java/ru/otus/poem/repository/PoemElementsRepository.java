package ru.otus.poem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.poem.model.PoemElement;

public interface PoemElementsRepository extends JpaRepository<PoemElement, Long> {
}
