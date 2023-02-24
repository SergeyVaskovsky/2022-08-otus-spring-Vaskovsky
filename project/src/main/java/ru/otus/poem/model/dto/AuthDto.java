package ru.otus.poem.model.dto;

import lombok.Value;

@Value
public class AuthDto {
    Long id;
    String name;
    String authorities;
}
