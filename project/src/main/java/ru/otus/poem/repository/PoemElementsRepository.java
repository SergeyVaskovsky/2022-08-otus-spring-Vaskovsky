package ru.otus.poem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.poem.model.PoemElement;

import java.util.List;

public interface PoemElementsRepository extends JpaRepository<PoemElement, Long> {

    List<PoemElement> findByPoemId(Long id);

}
