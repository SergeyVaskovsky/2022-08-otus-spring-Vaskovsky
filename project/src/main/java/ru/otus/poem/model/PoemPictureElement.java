package ru.otus.poem.model;

import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "poem_picture_element")
@NoArgsConstructor
@AllArgsConstructor
public class PoemPictureElement extends PoemElement {
    @Column(name = "picture")
    private byte[] picture;

    @Column(name = "scale")
    private Byte scale;

    public PoemPictureElement(long id, Poem poem, byte[] picture, Byte scale) {
        super(id, poem);
        this.picture = picture;
        this.scale = scale;
    }
}
