package ru.otus.homework10.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework10.mapping.AuthorDto;
import ru.otus.homework10.model.Author;
import ru.otus.homework10.service.AuthorService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private AuthorService authorService;

    @Test
    public void shouldReturnCorrectAuthorList() throws Exception {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author(1L, "Писатель"));
        authors.add(new Author(2L, "Писатель 2"));
        given(authorService.getAll()).willReturn(authors);

        List<AuthorDto> expectedResult = authors.stream()
                .map(a -> new AuthorDto(a.getId(), a.getName())).collect(Collectors.toList());

        mockMvc
                .perform(get("/api/authors"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

}