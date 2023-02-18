package ru.otus.poem.model.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PoemTextElementDto.class, name = "text"),
        @JsonSubTypes.Type(value = PoemPictureElementDto.class, name = "picture")
})
@Getter
@Setter
//@AllArgsConstructor
public abstract class PoemElementDto {
    private Long id;
    //private String type;
    private Long poemId;

    public PoemElementDto(Long id, Long poemId) {
        this.id = id;
        this.poemId = poemId;
    }
}
