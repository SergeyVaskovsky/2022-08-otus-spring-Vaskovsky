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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.otus.poem.model.dto.CommentDto;
import ru.otus.poem.service.CommentService;
import ru.otus.poem.service.PoemService;
import ru.otus.poem.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Autowired
    private UserService userService;
    @Autowired
    private PoemService poemService;
    @Autowired
    private CommentService commentService;

    private static Stream<Arguments> provideParamsForPost() {

        return Stream.of(
                Arguments.of("myemail@yandex.ru", "WRITER", status().isOk()),
                Arguments.of("myemail@yandex.ru", "READER", status().isForbidden())
        );
    }

    private static Stream<Arguments> provideParamsForPutAndDelete() {

        return Stream.of(
                Arguments.of("myemail@yandex.ru", "MODERATOR", status().isOk()),
                Arguments.of("myemail@yandex.ru", "WRITER", status().isForbidden())
        );
    }

    private static Stream<Arguments> provideParamsForGet() {

        return Stream.of(
                Arguments.of("myemail@yandex.ru", "WRITER", status().isForbidden()),
                Arguments.of("myemail@yandex.ru", "READER", status().isOk())
        );
    }

    @ParameterizedTest
    @MethodSource("provideParamsForPost")
    void shouldCorrectSaveNewComment(String userName, String roleName, ResultMatcher statusMatcher) throws Exception {
        CommentDto commentDto = new CommentDto(
                2L,
                "Очень понравилось",
                userService.getById(1L),
                poemService.getById(1L),
                null,
                LocalDateTime.now(),
                false);
        String expectedResult = mapper.writeValueAsString(commentDto);

        ResultActions resultActions = mockMvc.perform(post("/api/comments")
                        .with(csrf()).with(user(userName).authorities(new SimpleGrantedAuthority(roleName)))
                        .contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(statusMatcher);

        if (statusMatcher.equals(status().isOk())) {
            resultActions.andExpect(content().json(expectedResult));
        }
    }

    @ParameterizedTest
    @MethodSource("provideParamsForPutAndDelete")
    void shouldCorrectUpdateComment(String userName, String roleName, ResultMatcher statusMatcher) throws Exception {
        CommentDto commentDto = commentService.getById(1L);
        CommentDto changedCommentDto = new CommentDto(
                commentDto.getId(),
                commentDto.getText(),
                commentDto.getUser(),
                commentDto.getPoem(),
                commentDto.getRootComment(),
                commentDto.getPublishTime(),
                true
        );
        String expectedResult = mapper.writeValueAsString(changedCommentDto);

        ResultActions resultActions = mockMvc.perform(put("/api/comments/1")
                        .with(csrf()).with(user(userName).authorities(new SimpleGrantedAuthority(roleName)))
                        .contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(statusMatcher);

        if (statusMatcher.equals(status().isOk())) {
            resultActions.andExpect(content().json(expectedResult));
        }
    }

    @ParameterizedTest
    @MethodSource("provideParamsForPutAndDelete")
    void shouldCorrectDeleteComment(String userName, String roleName, ResultMatcher statusMatcher) throws Exception {
        mockMvc.perform(delete("/api/comments/2")
                        .with(csrf()).with(user(userName).authorities(new SimpleGrantedAuthority(roleName)))
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(statusMatcher);
    }

    @ParameterizedTest
    @MethodSource("provideParamsForGet")
    void shouldCorrectGetCommentsByPoemId(String userName, String roleName, ResultMatcher statusMatcher) throws Exception {
        String expectedResult = mapper.writeValueAsString(List.of(commentService.getById(1L)));
        ResultActions resultActions = mockMvc.perform(get("/api/comments/poem/1")
                        .with(user(userName).authorities(new SimpleGrantedAuthority(roleName))))
                .andExpect(statusMatcher);

        if (statusMatcher.equals(status().isOk())) {
            resultActions.andExpect(content().json(expectedResult));
        }
    }
}
