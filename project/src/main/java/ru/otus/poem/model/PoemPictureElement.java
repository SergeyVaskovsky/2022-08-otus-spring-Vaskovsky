package ru.otus.poem.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "poem_picture_element")
@NoArgsConstructor
@AllArgsConstructor
public class PoemPictureElement extends PoemElement {
    @Column(name = "picture")
    byte[] picture;
}
