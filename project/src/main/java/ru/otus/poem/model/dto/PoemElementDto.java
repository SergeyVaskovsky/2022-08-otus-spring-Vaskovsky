package ru.otus.poem.model.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.otus.poem.model.PoemElement;
import ru.otus.poem.model.PoemPictureElement;
import ru.otus.poem.model.PoemTextElement;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PoemTextElementDto.class, name = "text"),
        @JsonSubTypes.Type(value = PoemPictureElementDto.class, name = "picture")
})
@Getter
@Setter
@AllArgsConstructor
public abstract class PoemElementDto {
    private Long id;
    private String type;

    public static PoemElementDto toDto(PoemElement element) {
        return element instanceof PoemTextElement ?
                PoemTextElementDto.toDto((PoemTextElement) element) :
                PoemPictureElementDto.toDto((PoemPictureElement) element);
    }

}
