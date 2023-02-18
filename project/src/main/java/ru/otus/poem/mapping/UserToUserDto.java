package ru.otus.poem.mapping;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.otus.poem.model.User;
import ru.otus.poem.model.dto.UserDto;

@Service
public class UserToUserDto implements Converter<User, UserDto> {

    @Override
    public UserDto convert(User source) {
        return new UserDto(
                source.getId(),
                source.getName(),
                source.getPassword(),
                source.getEmail(),
                source.getRoles()
        );
    }
}
