package ru.otus.homework11.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.otus.homework11.mapping.GenreDto;
import ru.otus.homework11.model.Genre;
import ru.otus.homework11.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = {GenreController.class, ObjectMapper.class})
public class GenreControllerTest {

    @Autowired
    private GenreController genreController;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private GenreRepository genreRepository;

    @Test
    public void shouldReturnCorrectGenresList() throws Exception {
        WebTestClient client = WebTestClient
                .bindToController(genreController)
                .build();

        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre("1", "Жанр"));
        genres.add(new Genre("2", "Жанр 2"));
        given(genreRepository.findAll()).willReturn(Flux.just(genres.get(0), genres.get(1)));

        List<GenreDto> expectedResult = genres.stream()
                .map(g -> new GenreDto(g.getId(), g.getName())).collect(Collectors.toList());

        client.get()
                .uri("/api/genres")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody().json(mapper.writeValueAsString(expectedResult));
    }

}
