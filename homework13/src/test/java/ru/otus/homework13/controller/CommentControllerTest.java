package ru.otus.homework13.controller;

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
import ru.otus.homework13.mapping.CommentDto;
import ru.otus.homework13.model.Author;
import ru.otus.homework13.model.Book;
import ru.otus.homework13.model.Comment;
import ru.otus.homework13.model.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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

    private static Stream<Arguments> provideParamsForTest() {
        return Stream.of(
                Arguments.of("admin", "ADMIN", status().isOk()),
                Arguments.of("user", "USER", status().isOk()),
                Arguments.of("someone", "SOMEONE", status().isForbidden())
        );
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTest")
    void shouldReturnCorrectCommentsByBookId(String user, String role, ResultMatcher statusMatcher) throws Exception {
        Book book = new Book(1L, "Роман", new Author(1L, "Писатель"), new Genre(1L, "Для женщин"));
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(1L, "Хорошо", book));
        comments.add(new Comment(2L, "Плохо", book));
        comments.add(new Comment(3L, "Средненько", book));

        List<CommentDto> expectedResult = comments.stream().map(CommentDto::toDto).collect(Collectors.toList());

        ResultActions resultActions = mockMvc
                .perform(get("/api/books/1/comments").with(user(user).authorities(new SimpleGrantedAuthority(role))))
                .andExpect(statusMatcher);

        if (!"SOMEONE".equals(role)) {
            resultActions.andExpect(content().json(mapper.writeValueAsString(expectedResult)));
        }
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTest")
    void shouldCorrectSaveNewComment(String user, String role, ResultMatcher statusMatcher) throws Exception {
        Book book = new Book(1L, "Роман", new Author(1L, "Писатель"), new Genre(1L, "Для женщин"));
        Comment comment = new Comment(4L, "Хорошая книга", book);

        String expectedResult = mapper.writeValueAsString(CommentDto.toDto(comment));

        ResultActions resultActions = mockMvc.perform(post("/api/books/1/comments")
                        .with(csrf()).with(user(user).authorities(new SimpleGrantedAuthority(role)))
                        .contentType(APPLICATION_JSON)
                        .content(comment.getDescription()))
                .andExpect(statusMatcher);

        if (!"SOMEONE".equals(role)) {
            resultActions.andExpect(content().json(expectedResult));
        }
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTest")
    void shouldCorrectDeleteComment(String user, String role, ResultMatcher statusMatcher) throws Exception {
        mockMvc.perform(delete("/api/books/comments/1")
                        .with(csrf()).with(user(user).authorities(new SimpleGrantedAuthority(role))))
                .andExpect(statusMatcher);

    }

}