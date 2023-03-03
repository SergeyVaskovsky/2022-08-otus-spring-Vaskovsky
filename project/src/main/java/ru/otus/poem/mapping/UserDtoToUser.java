package ru.otus.poem.mapping;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.otus.poem.model.User;
import ru.otus.poem.model.dto.UserDto;

@Service
public class UserDtoToUser implements Converter<UserDto, User> {
    @Override
    public User convert(UserDto source) {
        return new User(
                source.getId(),
                source.getName(),
                source.getPassword(),
                source.getEmail(),
                source.getRoles()
        );
    }
}
