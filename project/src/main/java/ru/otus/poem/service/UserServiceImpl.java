package ru.otus.poem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.otus.poem.exception.UserNotFoundException;
import ru.otus.poem.model.Role;
import ru.otus.poem.model.User;
import ru.otus.poem.model.dto.UserDto;
import ru.otus.poem.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final static List<Role> STANDARD_ROLES = List.of(new Role(2, "READER"), new Role(3, "WRITER"));
    private final UserRepository userRepository;
    private final ConversionService conversionService;

    @Override
    public UserDto addNewUser(UserDto userDto) {
        User user = conversionService.convert(userDto, User.class);
        user.setRoles(STANDARD_ROLES);
        return conversionService.convert(userRepository.save(user), UserDto.class);
    }

    @Override
    public UserDto getById(Long id) {
        return conversionService.convert(
                userRepository
                        .findById(id)
                        .orElseThrow(() -> new UserNotFoundException("User not found by " + id)),
                UserDto.class);
    }
}
