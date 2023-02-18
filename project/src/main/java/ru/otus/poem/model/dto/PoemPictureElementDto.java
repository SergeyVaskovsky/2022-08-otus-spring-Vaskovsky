package ru.otus.poem.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PoemPictureElementDto extends PoemElementDto {
    private byte[] picture;
    private Byte scale;

    public PoemPictureElementDto(long id, /*String type,*/ Long poemId, byte[] picture, Byte scale) {
        super(id, /*type,*/ poemId);
        this.picture = picture;
        this.scale = scale;
    }
}
