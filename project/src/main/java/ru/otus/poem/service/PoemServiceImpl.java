package ru.otus.poem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.poem.exception.IORuntimeException;
import ru.otus.poem.exception.PoemNotFoundException;
import ru.otus.poem.model.Poem;
import ru.otus.poem.model.PoemElement;
import ru.otus.poem.model.PoemPictureElement;
import ru.otus.poem.model.PoemTextElement;
import ru.otus.poem.model.dto.PoemDto;
import ru.otus.poem.model.dto.PoemPictureElementDto;
import ru.otus.poem.model.dto.PoemTextElementDto;
import ru.otus.poem.repository.PoemElementsRepository;
import ru.otus.poem.repository.PoemRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PoemServiceImpl implements PoemService{

    private final PoemRepository poemRepository;
    private final PoemElementsRepository poemElementsRepository;

    @Override
    public List<PoemDto> getAll() {
        return poemRepository
                .findAll()
                .stream()
                .map(PoemDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PoemDto getById(Long id) {
        Poem poem = poemRepository
                .findById(id)
                .orElseThrow(() -> new PoemNotFoundException("Poem not found by id = " + id));
        return PoemDto.toDto(poem);
    }

    @Transactional
    @Override
    public PoemDto addNewPoem(PoemDto poemDto) {
        Poem poem = new Poem(poemDto.getTitle());
        Poem savedPoem = poemRepository.save(poem);
        return PoemDto.toDto(savedPoem);
    }

    @Transactional
    @Override
    public PoemDto updatePoem(Long id, PoemDto poemDto) {
        Poem poem = poemRepository
                .findById(id)
                .orElseThrow(() -> new PoemNotFoundException("Poem not found by id = " + id));
        poem.setTitle(poemDto.getTitle());
        Poem savedPoem = poemRepository.save(poem);
        return PoemDto.toDto(savedPoem);
    }
}
