package ru.otus.poem.model.dto;

import lombok.Value;
import ru.otus.poem.model.Role;
import ru.otus.poem.model.User;

import java.util.List;

@Value
public class UserDto {
    Long id;
    String name;
    String password;
    String email;
    List<Role> roles;

    public static UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getPassword(),
                user.getEmail(),
                user.getRoles()
        );
    }

    public static User toEntity(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getPassword(),
                userDto.getEmail(),
                userDto.getRoles()
        );
    }
}
