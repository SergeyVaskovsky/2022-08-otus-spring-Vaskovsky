package ru.otus.poem.model.dto;

import lombok.Value;
import ru.otus.poem.model.Poem;

import java.time.LocalDateTime;

@Value
public class PoemDto {
    long id;
    String title;
    LocalDateTime publishTime;

    public static PoemDto toDto(Poem poem) {
        return new PoemDto(
                poem.getId(),
                poem.getTitle(),
                poem.getPublishTime()
        );
    }
}
