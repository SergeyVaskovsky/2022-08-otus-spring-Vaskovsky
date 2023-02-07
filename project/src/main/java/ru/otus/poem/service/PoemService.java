package ru.otus.poem.service;

import ru.otus.poem.exception.PoemNotFoundException;
import ru.otus.poem.model.Poem;
import ru.otus.poem.model.dto.PoemDto;

import java.util.List;
import java.util.stream.Collectors;

public interface PoemService {
    List<PoemDto> getAll();

    PoemDto getById(Long id);

    PoemDto addNewPoem(PoemDto poemDto);

    PoemDto updatePoem(Long id, PoemDto poemDto);
}
