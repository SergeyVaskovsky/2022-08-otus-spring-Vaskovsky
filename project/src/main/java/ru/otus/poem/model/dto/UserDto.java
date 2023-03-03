package ru.otus.poem.model.dto;

import lombok.Value;
import ru.otus.poem.model.Role;

import java.util.List;

@Value
public class UserDto {
    Long id;
    String name;
    String password;
    String email;
    List<Role> roles;
}
