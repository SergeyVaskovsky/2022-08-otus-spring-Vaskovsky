package ru.otus.homework18.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework18.mapping.CommentDto;
import ru.otus.homework18.model.Author;
import ru.otus.homework18.model.Book;
import ru.otus.homework18.model.Comment;
import ru.otus.homework18.model.Genre;
import ru.otus.homework18.service.CommentService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private CommentService commentService;

    @Test
    void shouldReturnCorrectCommentsByBookId() throws Exception {
        Book book = new Book(1L, "Роман", new Author(1L, "Писатель"), new Genre(1L, "Для женщин"));
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(1L, "123", book));
        comments.add(new Comment(2L, "456", book));
        given(commentService.getAll(book.getId())).willReturn(comments);
        List<CommentDto> expectedResult = comments.stream().map(CommentDto::toDto).collect(Collectors.toList());

        mockMvc
                .perform(get("/api/books/1/comments"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    void shouldCorrectSaveNewComment() throws Exception {
        Book book = new Book(1L, "Роман", new Author(1L, "Писатель"), new Genre(1L, "Для женщин"));
        Comment comment = new Comment(1L, "Хорошая книга", book);
        given(commentService.upsert(0, comment.getDescription(), book.getId())).willReturn(comment);
        String expectedResult = mapper.writeValueAsString(CommentDto.toDto(comment));

        mockMvc.perform(post("/api/books/1/comments").contentType(APPLICATION_JSON)
                        .content(comment.getDescription()))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));
    }

    @Test
    void shouldCorrectDeleteComment() throws Exception {
        mockMvc.perform(delete("/api/books/comments/1"))
                .andExpect(status().isOk());
        verify(commentService, times(1)).delete(1L);
    }

}