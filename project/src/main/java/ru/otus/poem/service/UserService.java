package ru.otus.poem.service;

import ru.otus.poem.model.dto.UserDto;

public interface UserService {
    UserDto addNewUser(UserDto userDto);
}
