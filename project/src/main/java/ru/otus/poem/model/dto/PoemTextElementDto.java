package ru.otus.poem.model.dto;

import lombok.Value;
import ru.otus.poem.model.Poem;
import ru.otus.poem.model.PoemTextElement;

@Value
public class PoemTextElementDto extends PoemElementDto {
    String content;

    public PoemTextElementDto(long id, String type, String content) {
        this.id = id;
        this.type = type;
        this.content = content;
    }

    public static PoemTextElementDto toDto(PoemTextElement element) {
        return new PoemTextElementDto(
                element.getId(),
                "text",
                element.getContent()
        );
    }
}
