package ru.otus.homework10.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework10.mapping.GenreDto;
import ru.otus.homework10.model.Genre;
import ru.otus.homework10.service.GenreService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
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

    @Test
    public void shouldReturnCorrectGenreList() throws Exception {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(1L, "Жанр"));
        genres.add(new Genre(2L, "Жанр 2"));
        given(genreService.getAll()).willReturn(genres);

        List<GenreDto> expectedResult = genres.stream()
                .map(g -> new GenreDto(g.getId(), g.getName())).collect(Collectors.toList());

        mockMvc
                .perform(get("/api/genres"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }
}