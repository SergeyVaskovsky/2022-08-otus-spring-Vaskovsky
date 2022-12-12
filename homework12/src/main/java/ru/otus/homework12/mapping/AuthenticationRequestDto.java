package ru.otus.homework12.mapping;

import lombok.Value;

@Value
public class AuthenticationRequestDto {
    String username;
    String password;
}
