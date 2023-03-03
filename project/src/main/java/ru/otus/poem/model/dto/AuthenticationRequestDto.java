package ru.otus.poem.model.dto;

import lombok.Value;

@Value
public class AuthenticationRequestDto {
    String username;
    String password;
}