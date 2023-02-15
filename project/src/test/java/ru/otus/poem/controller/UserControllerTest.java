package ru.otus.poem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.otus.poem.model.Role;
import ru.otus.poem.model.dto.UserDto;

import java.util.List;
import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    private static Stream<Arguments> provideParamsForTest() {
        return Stream.of(
                Arguments.of(new UserDto(
                        4L,
                        "Sergey",
                        "123",
                        "myemail@yandex.ru",
                        List.of(new Role(4L, "MODERATOR"))), status().isOk())
        );
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTest")
    void shouldCorrectSaveNewUser(UserDto userDto, ResultMatcher statusMatcher) throws Exception {

        String expectedResult = mapper.writeValueAsString(userDto);

        mockMvc.perform(post("/api/users")
                        .contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(statusMatcher)
                .andExpect(content().json(expectedResult));

    }
}




