package ru.otus.poem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import ru.otus.poem.model.PoemPictureElement;
import ru.otus.poem.model.PoemTextElement;

@Getter
@Setter
@AllArgsConstructor
public class PoemPictureElementDto extends PoemElementDto {
    byte[] picture;

    public PoemPictureElementDto(long id, String type, byte[] picture) {
        this.id = id;
        this.type = type;
        this.picture = picture;
    }

    public static PoemPictureElementDto toDto(PoemPictureElement element) {
        return new PoemPictureElementDto(
                element.getId(),
                "picture",
                element.getPicture()
        );
    }
}
