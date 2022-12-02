package ru.otus.homework11.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.otus.homework11.mapping.AuthorDto;
import ru.otus.homework11.model.Author;
import ru.otus.homework11.repository.AuthorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = {AuthorController.class, ObjectMapper.class})
public class AuthorControllerTest {

    @Autowired
    private AuthorController authorController;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private AuthorRepository authorRepository;

    @Test
    public void shouldReturnCorrectAuthorsList() throws Exception {
        WebTestClient client = WebTestClient
                .bindToController(authorController)
                .build();

        List<Author> authors = new ArrayList<>();
        authors.add(new Author("1", "Писатель"));
        authors.add(new Author("2", "Писатель 2"));
        given(authorRepository.findAll()).willReturn(Flux.just(authors.get(0), authors.get(1)));

        List<AuthorDto> expectedResult = authors.stream()
                .map(a -> new AuthorDto(a.getId(), a.getName())).collect(Collectors.toList());

        client.get()
                .uri("/books/authors")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody().json(mapper.writeValueAsString(expectedResult));
    }

}
