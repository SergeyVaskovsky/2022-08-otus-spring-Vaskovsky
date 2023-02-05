package ru.otus.poem.model.dto;

import lombok.Value;
import ru.otus.poem.model.Poem;
import ru.otus.poem.model.PoemTextElement;

@Value
public class PoemTextElementDto extends PoemElementDto {
    long id;
    String content;

    public static PoemTextElementDto toDto(PoemTextElement element) {
        return new PoemTextElementDto(
                element.getId(),
                element.getContent()
        );
    }
}
