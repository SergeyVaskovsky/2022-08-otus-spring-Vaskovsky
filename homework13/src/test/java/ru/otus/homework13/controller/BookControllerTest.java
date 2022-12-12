package ru.otus.homework13.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework13.mapping.BookDto;
import ru.otus.homework13.model.Author;
import ru.otus.homework13.model.Book;
import ru.otus.homework13.model.Genre;
import ru.otus.homework13.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private BookService bookService;

    @WithMockUser(
            username = "admin"
    )
    @Test
    public void shouldReturnCorrectBookList() throws Exception {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Роман", new Author(1L, "Писатель"), new Genre(1L, "Для женщин")));
        books.add(new Book(2L, "Повесть", new Author(2L, "Писатель 2"), new Genre(2L, "Беллитристика")));
        books.add(new Book(3L, "Статья", new Author(3L, "Ученый"), new Genre(3L, "Наука")));
        given(bookService.getAll()).willReturn(books);

        List<BookDto> expectedResult = books.stream()
                .map(BookDto::toDto).collect(Collectors.toList());

        mockMvc
                .perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    public void shouldReturn401ForBooks() throws Exception {
        mockMvc
                .perform(get("/api/books"))
                .andExpect(status().isUnauthorized());
    }


    @WithMockUser(
            username = "admin"
    )
    @Test
    void shouldReturnCorrectBookById() throws Exception {
        Book book = new Book(1L, "Роман", new Author(1L, "Писатель"), new Genre(1L, "Для женщин"));
        given(bookService.getById(book.getId())).willReturn(book);
        BookDto expectedResult = BookDto.toDto(book);

        mockMvc
                .perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    public void shouldReturn401ForBook() throws Exception {
        mockMvc
                .perform(get("/api/books/1"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(
            username = "admin"
    )
    @Test
    void shouldCorrectDeleteBook() throws Exception {
        mockMvc.perform(delete("/api/books/1").with(csrf()))
                .andExpect(status().isOk());
        verify(bookService, times(1)).delete(1L);
    }

    @Test
    public void shouldReturn403ForDeleteBook() throws Exception {
        mockMvc
                .perform(delete("/api/books/1"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(
            username = "admin"
    )
    @Test
    void shouldCorrectSaveNewBook() throws Exception {
        Book book = new Book(0, "Роман", new Author(1L, "Писатель"), new Genre(1L, "Для женщин"));
        given(bookService.upsert(book.getId(), book.getName(), book.getAuthor().getId(), book.getGenre().getId())).willReturn(book);
        String expectedResult = mapper.writeValueAsString(BookDto.toDto(book));

        mockMvc.perform(post("/api/books").with(csrf()).contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn403ForSaveNewBook() throws Exception {
        Book book = new Book(0, "Роман", new Author(1L, "Писатель"), new Genre(1L, "Для женщин"));
        given(bookService.upsert(book.getId(), book.getName(), book.getAuthor().getId(), book.getGenre().getId())).willReturn(book);
        String expectedResult = mapper.writeValueAsString(BookDto.toDto(book));

        mockMvc.perform(post("/api/books").contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(
            username = "admin"
    )
    @Test
    void shouldCorrectUpdateBook() throws Exception {
        Book book = new Book(1L, "Роман", new Author(1L, "Писатель"), new Genre(1L, "Для женщин"));
        given(bookService.upsert(book.getId(), book.getName(), book.getAuthor().getId(), book.getGenre().getId())).willReturn(book);
        String expectedResult = mapper.writeValueAsString(BookDto.toDto(book));

        mockMvc.perform(put("/api/books/1").with(csrf()).contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn403ForUpdateBook() throws Exception {
        Book book = new Book(1L, "Роман", new Author(1L, "Писатель"), new Genre(1L, "Для женщин"));
        given(bookService.upsert(book.getId(), book.getName(), book.getAuthor().getId(), book.getGenre().getId())).willReturn(book);
        String expectedResult = mapper.writeValueAsString(BookDto.toDto(book));

        mockMvc.perform(put("/api/books/1").with(csrf()).contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(status().isUnauthorized());
    }
}