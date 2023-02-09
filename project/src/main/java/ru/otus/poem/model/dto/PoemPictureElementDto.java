package ru.otus.poem.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.poem.model.PoemPictureElement;

@Getter
@Setter
public class PoemPictureElementDto extends PoemElementDto {
    private byte[] picture;
    private Byte scale;

    public PoemPictureElementDto(long id, String type, byte[] picture, Byte scale) {
        super(id, type);
        this.picture = picture;
        this.scale = scale;
    }

    public static PoemPictureElementDto toDto(PoemPictureElement element) {
        return new PoemPictureElementDto(
                element.getId(),
                "picture",
                element.getPicture(),
                element.getScale()
        );
    }
}
