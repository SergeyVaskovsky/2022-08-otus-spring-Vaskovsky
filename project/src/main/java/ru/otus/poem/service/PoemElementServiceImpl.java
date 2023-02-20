package ru.otus.poem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.otus.poem.exception.PoemElementNotFoundException;
import ru.otus.poem.model.PoemElement;
import ru.otus.poem.model.PoemPictureElement;
import ru.otus.poem.model.PoemTextElement;
import ru.otus.poem.model.dto.PoemElementDto;
import ru.otus.poem.model.dto.PoemTextElementDto;
import ru.otus.poem.repository.PoemElementsRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PoemElementServiceImpl implements PoemElementService {

    private final PoemElementsRepository poemElementsRepository;
    private final PoemService poemService;
    private final ConversionService conversionService;

    @Override
    public List<PoemElementDto> getAll(Long id) {
        return poemElementsRepository
                .findByPoemId(id)
                .stream()
                .map(it -> conversionService.convert(it, PoemElementDto.class))
                .sorted(Comparator.comparingLong(poemElementDto -> poemElementDto != null ? poemElementDto.getId() : null))
                .collect(Collectors.toList());
    }

    @Override
    public PoemElementDto getById(Long id) {
        PoemElement poemElement =
                poemElementsRepository
                        .findById(id)
                        .orElseThrow(() -> new PoemElementNotFoundException("Poem element not found by id = " + id));
        return conversionService.convert(poemElement, PoemElementDto.class);
    }

    @Override
    public PoemElementDto addNewPoemElement(Long id, PoemElementDto poemElementDto) {
        poemElementDto.setPoemId(id);
        PoemElement poemElement = conversionService.convert(poemElementDto, PoemElement.class);
        PoemElement savedPoemElement = poemElementsRepository.save(poemElement);
        return conversionService.convert(savedPoemElement, PoemElementDto.class);
    }

    @Override
    public PoemElementDto updatePoemTextElement(Long id, PoemTextElementDto poemElementDto) {
        PoemTextElement poemElement = (PoemTextElement) poemElementsRepository
                .findById(id)
                .orElseThrow(() -> new PoemElementNotFoundException("Poem element not found by id = " + id));
        poemElement.setContent(poemElementDto.getContent());
        PoemElement savedPoemElement = poemElementsRepository.save(poemElement);
        return conversionService.convert(savedPoemElement, PoemElementDto.class);
    }

    @Override
    public PoemElementDto updatePoemPictureElement(Long id, byte[] picture, Byte scale) {
        PoemPictureElement poemElement = (PoemPictureElement) poemElementsRepository
                .findById(id)
                .orElseThrow(() -> new PoemElementNotFoundException("Poem element not found by id = " + id));
        if (picture != null) {
            poemElement.setPicture(picture);
        }
        poemElement.setScale(scale);
        PoemElement savedPoemElement = poemElementsRepository.save(poemElement);
        return conversionService.convert(savedPoemElement, PoemElementDto.class);
    }

    @Override
    public void deleteById(Long id) {
        poemElementsRepository.deleteById(id);
    }
}
