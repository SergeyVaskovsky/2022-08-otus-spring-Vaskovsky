package ru.otus.poem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/api/poems/{id}")
    public PoemDto getPoem(@PathVariable Long id) {
        return poemService.getById(id);
    }

    @PostMapping("/api/poems")
    public PoemDto addPoem(@RequestBody PoemDto poemDto) {
        return poemService.addNewPoem(poemDto);
    }

    @PutMapping("/api/poems/{id}")
    public PoemDto updatePoem(@PathVariable Long id, @RequestBody PoemDto poemDto) {
        return poemService.updatePoem(id, poemDto);
    }
}
