package ru.otus.poem.model.dto;

import lombok.Value;
import ru.otus.poem.model.Poem;
import ru.otus.poem.model.PoemPictureElement;
import ru.otus.poem.model.PoemTextElement;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class PoemDto {
    long id;
    String title;
    LocalDateTime publishTime;
    //List<PoemElementDto> elements;

    public static PoemDto toDto(Poem poem) {
        List<PoemElementDto> elements = new ArrayList<>();
        /*poem.getElements().forEach(element -> elements.add(element instanceof PoemTextElement
                ? PoemTextElementDto.toDto((PoemTextElement) element)
                : PoemPictureElementDto.toDto((PoemPictureElement) element)));*/
        return new PoemDto(
                poem.getId(),
                poem.getTitle(),
                poem.getPublishTime()/*,
                elements*/
        );
    }

    public static Poem toEntity(PoemDto poemDto) {
        return new Poem(
                poemDto.getId(),
                poemDto.getTitle(),
                /*poemDto.getElements()
                        .stream()
                        .map(element -> PoemElementDto.toEntity(element))
                        .collect(Collectors.toList()),*/
                poemDto.getPublishTime());
    }
}
