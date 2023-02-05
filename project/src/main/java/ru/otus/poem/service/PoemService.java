package ru.otus.poem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.poem.model.Poem;
import ru.otus.poem.model.dto.PoemDto;
import ru.otus.poem.repository.PoemRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PoemService {

    private final PoemRepository poemRepository;

    public List<PoemDto> getAll() {
        return poemRepository
                .findAll()
                .stream()
                .map(PoemDto::toDto)
                .collect(Collectors.toList());
    }

    public PoemDto addNewPoem(PoemDto poemDto) {
        Poem poem = new Poem(poemDto.getTitle());
        Poem savedPoem = poemRepository.save(poem);
        return PoemDto.toDto(savedPoem);
    }
}
