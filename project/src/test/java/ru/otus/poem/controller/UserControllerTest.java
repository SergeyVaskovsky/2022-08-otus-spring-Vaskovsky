package ru.otus.poem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.poem.bot.Bot;
import ru.otus.poem.exception.IncorrectEmailException;
import ru.otus.poem.exception.IncorrectNameException;
import ru.otus.poem.exception.IncorrectPasswordException;
import ru.otus.poem.model.Role;
import ru.otus.poem.model.dto.UserDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
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
    @MockBean
    private Bot bot;

    @Test
    void shouldCorrectSaveNewUser() throws Exception {
        UserDto userDto = new UserDto(
                4L,
                "Sergey",
                "12345678",
                "myemail@yandex.ru",
                List.of(new Role(2L, "READER"), new Role(3L, "WRITER")));

        String expectedResult = mapper.writeValueAsString(userDto);

        mockMvc.perform(post("/api/users")
                        .contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));

    }

    @Test
    void shouldCheckPasswordException_whenSaveNewUser() throws Exception {

        UserDto userDto = new UserDto(
                4L,
                "Sergey",
                "1234567",
                "myemail@yandex.ru",
                List.of(new Role(2L, "READER"), new Role(3L, "WRITER")));

        String expectedResult = mapper.writeValueAsString(userDto);

        mockMvc.perform(post("/api/users")
                        .contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IncorrectPasswordException));

    }

    @Test
    void shouldCheckUserNameException_whenSaveNewUser() throws Exception {

        UserDto userDto = new UserDto(
                4L,
                "",
                "1234567",
                "myemail@yandex.ru",
                List.of(new Role(2L, "READER"), new Role(3L, "WRITER")));

        String expectedResult = mapper.writeValueAsString(userDto);

        mockMvc.perform(post("/api/users")
                        .contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IncorrectNameException));

    }

    @Test
    void shouldCheckEmailNameException_whenSaveNewUser() throws Exception {

        UserDto userDto = new UserDto(
                4L,
                "Sergey",
                "1234567",
                "myemailyandex.ru",
                List.of(new Role(2L, "READER"), new Role(3L, "WRITER")));

        String expectedResult = mapper.writeValueAsString(userDto);

        mockMvc.perform(post("/api/users")
                        .contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IncorrectEmailException));

    }
}




