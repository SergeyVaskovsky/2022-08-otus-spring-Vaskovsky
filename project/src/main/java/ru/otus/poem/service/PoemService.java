package ru.otus.poem.service;

import ru.otus.poem.model.dto.PoemDto;

import java.util.List;

public interface PoemService {
    List<PoemDto> getAll(boolean readonly);

    PoemDto getById(Long id);
    PoemDto addNewPoem(PoemDto poemDto);
    PoemDto updatePoem(Long id, PoemDto poemDto);
}
