package ru.otus.homework13.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.otus.homework13.mapping.GenreDto;
import ru.otus.homework13.model.Genre;
import ru.otus.homework13.service.GenreService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreController.class)
public class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private GenreService genreService;

    private static Stream<Arguments> provideParamsForTest() {
        return Stream.of(
                Arguments.of("admin", "ADMIN", status().isOk()),
                Arguments.of("user", "USER", status().isOk()),
                Arguments.of("someone", "SOMEONE", status().isUnauthorized())
        );
    }


    @ParameterizedTest
    @MethodSource("provideParamsForTest")
    public void shouldReturnCorrectGenreList(String user, String roles, ResultMatcher statusMatcher) throws Exception {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(1L, "Жанр"));
        if ("admin".equals(user)) {
            genres.add(new Genre(2L, "Жанр 2"));
        }

        given(genreService.getAll()).willReturn(genres);

        List<GenreDto> expectedResult = genres.stream()
                .map(g -> new GenreDto(g.getId(), g.getName())).collect(Collectors.toList());

        mockMvc
                .perform(get("/api/genres").with(user(user).roles(roles)))
                .andExpect(statusMatcher)
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }
}