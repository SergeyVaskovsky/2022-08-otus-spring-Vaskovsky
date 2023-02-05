package ru.otus.poem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.poem.model.Poem;
import ru.otus.poem.model.dto.PoemDto;
import ru.otus.poem.service.PoemService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PoemController {
    private final PoemService poemService;

    @GetMapping("/api/poems")
    public List<PoemDto> getPoems() {
        return poemService.getAll();
    }
    @PostMapping("/api/poems")
    public PoemDto addPoem(@RequestBody PoemDto poemDto) {
        return poemService.addNewPoem(poemDto);
    }
}
