package ru.otus.poem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.poem.model.dto.UserDto;
import ru.otus.poem.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/api/users")
    public UserDto addNewUser(@RequestBody UserDto userDto) {
        UserDto newUser = new UserDto(
                -1L,
                userDto.getName(),
                userDto.getPassword(),
                userDto.getEmail(),
                userDto.getRoles()
        );
        return userService.addNewUser(newUser);
    }
}
