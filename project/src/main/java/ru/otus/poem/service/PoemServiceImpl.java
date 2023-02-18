package ru.otus.poem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.otus.poem.exception.PoemNotFoundException;
import ru.otus.poem.model.Poem;
import ru.otus.poem.model.dto.PoemDto;
import ru.otus.poem.repository.PoemRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PoemServiceImpl implements PoemService{

    private final PoemRepository poemRepository;
    private final ConversionService conversionService;

    @Override
    public List<PoemDto> getAll() {
        return poemRepository
                .findAll()
                .stream()
                .map(it -> conversionService.convert(it, PoemDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PoemDto getById(Long id) {
        Poem poem = poemRepository
                .findById(id)
                .orElseThrow(() -> new PoemNotFoundException("Poem not found by id = " + id));
        return conversionService.convert(poem, PoemDto.class);
    }

    @Override
    public PoemDto addNewPoem(PoemDto poemDto) {
        Poem poem = new Poem(poemDto.getTitle());
        Poem savedPoem = poemRepository.save(poem);
        return conversionService.convert(savedPoem, PoemDto.class);
    }

    @Override
    public PoemDto updatePoem(Long id, PoemDto poemDto) {
        Poem poem = poemRepository
                .findById(id)
                .orElseThrow(() -> new PoemNotFoundException("Poem not found by id = " + id));
        poem.setTitle(poemDto.getTitle());
        Poem savedPoem = poemRepository.save(poem);
        return conversionService.convert(savedPoem, PoemDto.class);
    }
}
