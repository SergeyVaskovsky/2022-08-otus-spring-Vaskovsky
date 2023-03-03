package ru.otus.poem.model.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class PoemDto {
    long id;
    String title;
    LocalDateTime publishTime;
}
