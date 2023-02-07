package ru.otus.poem.model.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
public abstract class PoemElementDto {
    protected long id;
    protected String type;
}
