package ru.otus.homework13.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.otus.homework13.mapping.AuthorDto;
import ru.otus.homework13.model.Author;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerTest {

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
    public void shouldReturnCorrectAuthorList(String user, String role, ResultMatcher statusMatcher) throws Exception {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author(1L, "Достаевский Ф. М."));
        authors.add(new Author(2L, "Коллектив авторов"));

        List<AuthorDto> expectedResult = authors.stream()
                .map(a -> new AuthorDto(a.getId(), a.getName())).collect(Collectors.toList());

        ResultActions resultActions = mockMvc
                .perform(get("/api/authors").with(user(user).authorities(new SimpleGrantedAuthority(role))))
                .andExpect(statusMatcher);

        if (!"SOMEONE".equals(role)) {
            resultActions.andExpect(content().json(mapper.writeValueAsString(expectedResult)));
        }
    }
}