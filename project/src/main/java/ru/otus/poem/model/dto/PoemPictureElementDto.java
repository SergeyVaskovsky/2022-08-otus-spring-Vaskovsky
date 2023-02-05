package ru.otus.poem.model.dto;

import lombok.Value;
import ru.otus.poem.model.PoemPictureElement;
import ru.otus.poem.model.PoemTextElement;

@Value
public class PoemPictureElementDto extends PoemElementDto {
    long id;
    byte[] picture;

    public static PoemPictureElementDto toDto(PoemPictureElement element) {
        return new PoemPictureElementDto(
                element.getId(),
                element.getPicture()
        );
    }
}
