package ru.otus.poem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.poem.model.dto.PoemDto;
import ru.otus.poem.model.dto.PoemElementDto;
import ru.otus.poem.model.dto.PoemPictureElementDto;
import ru.otus.poem.model.dto.PoemTextElementDto;
import ru.otus.poem.service.PoemElementService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PoemElementController {

    private final PoemElementService poemElementService;
    @GetMapping("/api/poems/{id}/elements")
    public List<PoemElementDto> getPoemElements(@PathVariable Long id) {
        return poemElementService.getAll(id);
    }

    @PostMapping("/api/poems/{id}/elements")
    public PoemElementDto addPoemElement(@RequestBody PoemElementDto poemElementDto) {
        return poemElementService.addNewPoemElement(poemElementDto);
    }

    @PutMapping("/api/poems/text-elements/{id}")
    public PoemElementDto updatePoemTextElement(@PathVariable Long id, @RequestBody PoemTextElementDto poemTextElementDto) {
        return poemElementService.updatePoemTextElement(id, poemTextElementDto);
    }

    @PutMapping("/api/poems/picture-elements/{id}")
    public PoemElementDto updatePoemPictureElement(
            @PathVariable Long id,
            @RequestBody PoemPictureElementDto poemPictureElementDto,
            MultipartFile file) throws IOException {

        poemPictureElementDto.setPicture(file.getBytes());
        return poemElementService.updatePoemPictureElement(id, poemPictureElementDto);
    }

    @DeleteMapping("/api/poems/elements/{id}")
    public void deletePoemElement(@PathVariable Long id) {
        poemElementService.deleteById(id);
    }
}
