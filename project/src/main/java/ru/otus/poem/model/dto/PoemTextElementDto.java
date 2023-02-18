package ru.otus.poem.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PoemTextElementDto extends PoemElementDto {
    private String content;

    public PoemTextElementDto(long id, /*String type,*/ Long poemId, String content) {
        super(id, /*type,*/ poemId);
        this.content = content;
    }
}
