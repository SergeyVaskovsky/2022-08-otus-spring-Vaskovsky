package ru.otus.poem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.poem.exception.PoemElementNotFoundException;
import ru.otus.poem.model.PoemElement;
import ru.otus.poem.model.PoemPictureElement;
import ru.otus.poem.model.PoemTextElement;
import ru.otus.poem.model.dto.PoemElementDto;
import ru.otus.poem.model.dto.PoemPictureElementDto;
import ru.otus.poem.model.dto.PoemTextElementDto;
import ru.otus.poem.repository.PoemElementsRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PoemElementServiceImpl implements PoemElementService {

    private final PoemElementsRepository poemElementsRepository;

    @Override
    public List<PoemElementDto> getAll(Long id) {
        return poemElementsRepository
                .findByPoemId(id)
                .stream()
                .map(PoemElementDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PoemElementDto getById(Long id) {
        PoemElement poemElement =
                poemElementsRepository
                        .findById(id)
                        .orElseThrow(() -> new PoemElementNotFoundException("Poem element not found by id = " + id));
        return PoemElementDto.toDto(poemElement);
    }

    @Override
    public PoemElementDto addNewPoemElement(PoemElementDto poemElementDto) {
        PoemElement poemElement = PoemElementDto.toEntity(poemElementDto);
        PoemElement savedPoemElement = poemElementsRepository.save(poemElement);
        return PoemElementDto.toDto(savedPoemElement);
    }

    @Override
    public PoemElementDto updatePoemTextElement(Long id, PoemTextElementDto poemElementDto) {
        PoemTextElement poemElement = (PoemTextElement) poemElementsRepository
                .findById(id)
                .orElseThrow(() -> new PoemElementNotFoundException("Poem element not found by id = " + id));
        poemElement.setContent(poemElementDto.getContent());
        PoemElement savedPoemElement = poemElementsRepository.save(poemElement);
        return PoemElementDto.toDto(savedPoemElement);
    }

    @Override
    public PoemElementDto updatePoemPictureElement(Long id, PoemPictureElementDto poemElementDto) {
        PoemPictureElement poemElement = (PoemPictureElement) poemElementsRepository
                .findById(id)
                .orElseThrow(() -> new PoemElementNotFoundException("Poem element not found by id = " + id));
        poemElement.setPicture(poemElement.getPicture());
        PoemElement savedPoemElement = poemElementsRepository.save(poemElement);
        return PoemElementDto.toDto(savedPoemElement);
    }

    @Override
    public void deleteById(Long id) {
        poemElementsRepository.deleteById(id);
    }
}
