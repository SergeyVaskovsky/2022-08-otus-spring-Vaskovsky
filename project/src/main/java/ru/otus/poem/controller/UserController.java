package ru.otus.poem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.poem.exception.IncorrectEmailException;
import ru.otus.poem.exception.IncorrectNameException;
import ru.otus.poem.exception.IncorrectPasswordException;
import ru.otus.poem.model.dto.UserDto;
import ru.otus.poem.service.UserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
public class UserController {
    private static final long PASSWORD_MIN_LENGTH = 8;
    private static final String EMAIL = "^[a-zA-Z0-9_!#$%&'*+?{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+?{|}~^-]+)*@(?=.{4,253}$)((?:[a-zA-Zа-яА-ЯЁё0-9](?:[a-zA-Zа-яА-ЯЁё0-9-]{0,61}[a-zA-Zа-яА-ЯЁё0-9])?\\.)+[a-zA-Zа-яА-ЯЁё]{2,63}$)$";
    private final UserService userService;

    @PostMapping("/api/users")
    public ResponseEntity<?> addNewUser(@RequestBody UserDto userDto) {
        if (!isValidName(userDto.getName())) {
            throw new IncorrectNameException("Длина поле \"Как Вас зовут\" должно быть больше нуля");
        }

        if (!isValidEmail(userDto.getEmail())) {
            throw new IncorrectEmailException("Email " + userDto.getEmail() + " не корректный");
        }

        if (!isValidPassword(userDto.getPassword())) {
            throw new IncorrectPasswordException("Длина пароля должна быть больше либо равна " + PASSWORD_MIN_LENGTH + " символов");
        }

        UserDto newUser = new UserDto(
                -1L,
                userDto.getName(),
                userDto.getPassword(),
                userDto.getEmail(),
                userDto.getRoles()
        );
        return new ResponseEntity<>(userService.addNewUser(newUser), HttpStatus.OK);
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPassword(String password) {
        return password != null && password.length() >= PASSWORD_MIN_LENGTH;
    }

    private boolean isValidName(String name) {
        return name != null && name.length() > 0;
    }
}
