package ru.otus.homework13.mapping;

import lombok.Value;

@Value
public class AuthenticationRequestDto {
    String username;
    String password;
}
