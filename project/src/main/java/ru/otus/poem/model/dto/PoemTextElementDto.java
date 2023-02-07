package ru.otus.poem.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import ru.otus.poem.model.Poem;
import ru.otus.poem.model.PoemTextElement;

@Getter
@Setter
public class PoemTextElementDto extends PoemElementDto {
    private String content;

    public PoemTextElementDto(long id, String type, String content) {
        super(id, type);
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
