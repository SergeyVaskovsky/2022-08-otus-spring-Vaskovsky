package ru.otus.poem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.otus.poem.model.Role;
import ru.otus.poem.model.dto.CommentDto;
import ru.otus.poem.model.dto.PoemDto;
import ru.otus.poem.model.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    private static Stream<Arguments> provideParamsForTest() {
        CommentDto commentDto = new CommentDto(
                -1L,
                "Очень понравилось",
                new UserDto(
                        1L,
                        "Sergey",
                        "123",
                        "myemail@yandex.ru",
                        List.of(new Role(1L, "READER"), new Role(1L, "WRITER"))
                ),
                new PoemDto(1L, "Мое первое стихотворение", LocalDateTime.now()),
                null,
                LocalDateTime.now(),
                false);

        return Stream.of(
                Arguments.of(commentDto, "myemail@yandex.ru", "WRITER", status().isOk()),
                Arguments.of(commentDto, "myemail@yandex.ru", "READER", status().isForbidden())
        );
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTest")
    void shouldCorrectSaveNewComment(CommentDto commentDto, String userName, String roleName, ResultMatcher statusMatcher) throws Exception {

        String expectedResult = mapper.writeValueAsString(commentDto);

        mockMvc.perform(post("/api/comments")
                        .with(csrf()).with(user(userName).authorities(new SimpleGrantedAuthority(roleName)))
                        .contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(statusMatcher)
                .andExpect(content().json(expectedResult));

    }
}
