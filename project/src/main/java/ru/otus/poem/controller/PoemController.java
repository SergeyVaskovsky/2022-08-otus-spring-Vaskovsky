package ru.otus.poem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.poem.model.dto.PoemDto;
import ru.otus.poem.service.PoemServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PoemController {
    private final PoemServiceImpl poemServiceImpl;

    @GetMapping("/api/poems")
    public List<PoemDto> getPoems(@RequestParam(required = false) boolean readonly) {
        return poemServiceImpl.getAll(readonly);
    }

    @GetMapping("/api/poems/{id}")
    public PoemDto getPoem(@PathVariable Long id) {
        return poemServiceImpl.getById(id);
    }

    @PostMapping("/api/poems")
    public PoemDto addPoem(@RequestBody PoemDto poemDto) {
        return poemServiceImpl.addNewPoem(poemDto);
    }

    @PutMapping(path = "/api/poems/{id}")
    public PoemDto updatePoem(@PathVariable Long id, @RequestBody PoemDto poemDto) {
        return poemServiceImpl.updatePoem(id, poemDto);
    }
}
