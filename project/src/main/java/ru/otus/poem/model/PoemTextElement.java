package ru.otus.poem.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "poem_text_element")
@NoArgsConstructor
@AllArgsConstructor
public class PoemTextElement extends PoemElement {
    @Column(name = "content")
    String content;
}
