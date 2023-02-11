package ru.otus.poem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.poem.model.dto.UserDto;
import ru.otus.poem.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto addNewUser(UserDto userDto) {
        return UserDto.toDto(userRepository.save(UserDto.toEntity(userDto)));
    }
}
