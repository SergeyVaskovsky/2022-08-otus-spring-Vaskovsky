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
import ru.otus.homework13.mapping.BookDto;
import ru.otus.homework13.model.Author;
import ru.otus.homework13.model.Book;
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
public class BookControllerTest {

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

    private static Stream<Arguments> provideParamsForTestGetById() {
        return Stream.of(
                Arguments.of("admin", "ADMIN", status().isOk(), "1"),
                Arguments.of("user", "USER", status().isForbidden(), "2"),
                Arguments.of("user", "USER", status().isOk(), "3"),
                Arguments.of("someone", "SOMEONE", status().isForbidden(), "1")
        );
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTest")
    public void shouldReturnCorrectBookList(String user, String role, ResultMatcher statusMatcher) throws Exception {
        List<Book> books = new ArrayList<>();
        if ("ADMIN".equals(role)) {
            books.add(new Book(1L, "Преступление и наказание", new Author(1L, "Достаевский Ф. М."), new Genre(1L, "Триллер")));
            books.add(new Book(2L, "Идиот", new Author(1L, "Достаевский Ф. М."), new Genre(1L, "Триллер")));
        }
        books.add(new Book(3L, "Записки путешественника", new Author(2L, "Коллектив авторов"), new Genre(2L, "Научпоп")));

        List<BookDto> expectedResult = books.stream()
                .map(BookDto::toDto).collect(Collectors.toList());

        ResultActions resultActions = mockMvc
                .perform(get("/api/books").with(user(user).authorities(new SimpleGrantedAuthority(role))))
                .andExpect(statusMatcher);

        if (!"SOMEONE".equals(role)) {
            resultActions.andExpect(content().json(mapper.writeValueAsString(expectedResult)));
        }
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTestGetById")
    void shouldReturnCorrectBookById(String user, String role, ResultMatcher statusMatcher, String bookId) throws Exception {
        Book book;
        if ("ADMIN".equals(role)) {
            book = new Book(1L, "Преступление и наказание", new Author(1L, "Достаевский Ф. М."), new Genre(1L, "Триллер"));
        } else {
            book = new Book(3L, "Записки путешественника", new Author(2L, "Коллектив авторов"), new Genre(2L, "Научпоп"));
        }
        BookDto expectedResult = BookDto.toDto(book);

        ResultActions resultActions = mockMvc
                .perform(get("/api/books/" + bookId).with(user(user).authorities(new SimpleGrantedAuthority(role))))
                .andExpect(statusMatcher);

        if (!("SOMEONE".equals(role) || ("USER".equals(role) && "2".equals(bookId)))) {
            resultActions.andExpect(content().json(mapper.writeValueAsString(expectedResult)));
        }
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTestGetById")
    void shouldCorrectDeleteBook(String user, String role, ResultMatcher statusMatcher, String bookId) throws Exception {
        mockMvc.perform(delete("/api/books/" + bookId).with(csrf()).with(user(user).authorities(new SimpleGrantedAuthority(role))))
                .andExpect(statusMatcher);
    }

    private static Stream<Arguments> provideParamsForTestWithGenreId() {
        return Stream.of(
                Arguments.of("admin", "ADMIN", status().isOk(), 1),
                Arguments.of("user", "USER", status().isForbidden(), 1),
                Arguments.of("user", "USER", status().isOk(), 2),
                Arguments.of("someone", "SOMEONE", status().isForbidden(), 1)
        );
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTestWithGenreId")
    void shouldCorrectSaveNewBook(String user, String role, ResultMatcher statusMatcher, long genreId) throws Exception {
        Book book = new Book(0, "Роман", new Author(1L, "Писатель"), new Genre(genreId, "Для женщин"));

        String expectedResult = mapper.writeValueAsString(BookDto.toDto(book));

        mockMvc.perform(post("/api/books").with(csrf()).with(user(user).authorities(new SimpleGrantedAuthority(role)))
                        .contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(statusMatcher);
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTestWithGenreId")
    void shouldCorrectUpdateBook(String user, String role, ResultMatcher statusMatcher, long genreId) throws Exception {
        Book book = new Book(1L, "Роман", new Author(1L, "Писатель"), new Genre(genreId, "Для женщин"));

        String expectedResult = mapper.writeValueAsString(BookDto.toDto(book));

        mockMvc.perform(put("/api/books/1").with(csrf()).with(user(user).authorities(new SimpleGrantedAuthority(role)))
                        .contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(statusMatcher);
    }

}