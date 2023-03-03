package ru.otus.poem.service;

import ru.otus.poem.model.dto.PoemElementDto;
import ru.otus.poem.model.dto.PoemPictureElementDto;
import ru.otus.poem.model.dto.PoemTextElementDto;

import java.util.List;

public interface PoemElementService {
    List<PoemElementDto> getAll(Long id);

    PoemElementDto getById(Long id);

    PoemElementDto addNewPoemElement(Long id, PoemElementDto poemElementDto);

    PoemElementDto updatePoemTextElement(Long id, PoemTextElementDto poemElementDto);

    PoemElementDto updatePoemPictureElement(Long id, byte[] picture, Byte scale);

    void deleteById(Long id);
}
