package ru.otus.homework11.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework11.mapping.CommentDto;
import ru.otus.homework11.model.Author;
import ru.otus.homework11.model.Book;
import ru.otus.homework11.model.Comment;
import ru.otus.homework11.model.Genre;
import ru.otus.homework11.repository.BookRepository;
import ru.otus.homework11.repository.CommentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = {CommentController.class, ObjectMapper.class})
public class CommentControllerTest {

    @Autowired
    private CommentController commentController;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private CommentRepository commentRepository;

    @Test
    public void shouldReturnCorrectCommentsByBookId() throws Exception {
        WebTestClient client = WebTestClient
                .bindToController(commentController)
                .build();

        Book book = new Book("1", "Роман", new Author("1", "Писатель"), new Genre("1", "Для женщин"));
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment("1", "123", book));
        comments.add(new Comment("2", "456", book));
        given(commentRepository.findAllByBookId(book.getId())).willReturn(Flux.just(comments.get(0), comments.get(1)));
        List<CommentDto> expectedResult = comments.stream().map(CommentDto::toDto).collect(Collectors.toList());

        client.get()
                .uri("/books/1/comments")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody().json(mapper.writeValueAsString(expectedResult));
    }

    @Test
    void shouldCorrectDeleteComment() {
        WebTestClient client = WebTestClient
                .bindToController(commentController)
                .build();

        Book book = new Book("1", "Роман", new Author("1", "Писатель"), new Genre("1", "Для женщин"));

        given(commentRepository.deleteById(book.getId())).willReturn(Mono.empty());

        client.delete().uri("/books/comments/1")
                .exchange()
                .expectStatus()
                .isOk();
        verify(commentRepository, times(1)).deleteById("1");
    }

    @Test
    void shouldCorrectSaveNewComment() throws Exception {
        WebTestClient client = WebTestClient
                .bindToController(commentController)
                .build();

        Book book = new Book("1", "Роман", new Author("1", "Писатель"), new Genre("1", "Для женщин"));
        Comment comment = new Comment("Хорошая книга", book);
        given(commentRepository.save(comment)).willReturn(Mono.just(comment));
        given(bookRepository.findById(book.getId())).willReturn(Mono.just(book));

        String expectedResult = mapper.writeValueAsString(CommentDto.toDto(comment));

        client.post()
                .uri("/books/1/comments")
                .body(Mono.just(comment.getDescription()), String.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody().json(expectedResult);
    }
}