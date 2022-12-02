package ru.otus.homework11.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework11.mapping.BookDto;
import ru.otus.homework11.model.Author;
import ru.otus.homework11.model.Book;
import ru.otus.homework11.model.Genre;
import ru.otus.homework11.repository.AuthorRepository;
import ru.otus.homework11.repository.BookRepository;
import ru.otus.homework11.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = {BookController.class, ObjectMapper.class})
public class BookControllerTest {

    @Autowired
    private BookController bookController;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private GenreRepository genreRepository;

    @Test
    public void shouldReturnCorrectBookList() throws Exception {
        WebTestClient client = WebTestClient
                .bindToController(bookController)
                .build();

        List<Book> books = new ArrayList<>();
        books.add(new Book("1", "Роман", new Author("1", "Писатель"), new Genre("1", "Для женщин")));
        books.add(new Book("2", "Повесть", new Author("2", "Писатель 2"), new Genre("2", "Беллитристика")));
        books.add(new Book("3", "Статья", new Author("3", "Ученый"), new Genre("3", "Наука")));
        given(bookRepository.findAll()).willReturn(Flux.just(books.get(0), books.get(1), books.get(2)));

        List<BookDto> expectedResult = books.stream()
                .map(BookDto::toDto).collect(Collectors.toList());

        client.get()
                .uri("/books")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody().json(mapper.writeValueAsString(expectedResult));
    }

    @Test
    void shouldReturnBookById() {
        WebTestClient client = WebTestClient
                .bindToController(bookController)
                .build();

        Book book = new Book("1", "Роман", new Author("1", "Писатель"), new Genre("1", "Для женщин"));
        given(bookRepository.findById(book.getId())).willReturn(Mono.just(book));

        client.get()
                .uri("/books/1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody().jsonPath("name").isEqualTo("Роман")
                .jsonPath("authorName").isEqualTo("Писатель")
                .jsonPath("genreName").isEqualTo("Для женщин");
    }

    @Test
    void shouldCorrectDeleteBook() {
        WebTestClient client = WebTestClient
                .bindToController(bookController)
                .build();

        Book book = new Book("1", "Роман", new Author("1", "Писатель"), new Genre("1", "Для женщин"));

        given(bookRepository.deleteById(book.getId())).willReturn(Mono.empty());

        client.delete().uri("/books/1")
                .exchange()
                .expectStatus()
                .isOk();
        verify(bookRepository, times(1)).deleteById("1");
    }


    @Test
    void shouldCorrectSaveNewBook() {
        WebTestClient client = WebTestClient
                .bindToController(bookController)
                .build();
        Author author = new Author("1", "Писатель");
        Genre genre = new Genre("1", "Для женщин");
        Book book = new Book("0", "Роман", author, genre);
        given(authorRepository.findById("1")).willReturn(Mono.just(author));
        given(genreRepository.findById("1")).willReturn(Mono.just(genre));
        given(bookRepository.save(book)).willReturn(Mono.empty());

        client.post()
                .uri("/books")
                .body(Mono.just(BookDto.toDto(book)), BookDto.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void shouldCorrectUpdateBook() {
        WebTestClient client = WebTestClient
                .bindToController(bookController)
                .build();
        Author author = new Author("1", "Писатель");
        Genre genre = new Genre("1", "Для женщин");
        Book book = new Book("1", "Роман", author, genre);
        given(authorRepository.findById("1")).willReturn(Mono.just(author));
        given(genreRepository.findById("1")).willReturn(Mono.just(genre));
        given(bookRepository.save(book)).willReturn(Mono.empty());

        client.put()
                .uri("/books/1")
                .body(Mono.just(BookDto.toDto(book)), BookDto.class)
                .exchange()
                .expectStatus()
                .isOk();
    }
}
/*
    @Test
    public void shouldReturnCorrectAuthorList() throws Exception {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author(1L, "Писатель"));
        authors.add(new Author(2L, "Писатель 2"));
        given(authorService.getAll()).willReturn(authors);

        List<AuthorDto> expectedResult = authors.stream()
                .map(a -> new AuthorDto(a.getId(), a.getName())).collect(Collectors.toList());

        mockMvc
                .perform(get("/books/authors"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    public void shouldReturnCorrectGenreList() throws Exception {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(1L, "Жанр"));
        genres.add(new Genre(2L, "Жанр 2"));
        given(genreService.getAll()).willReturn(genres);

        List<GenreDto> expectedResult = genres.stream()
                .map(g -> new GenreDto(g.getId(), g.getName())).collect(Collectors.toList());

        mockMvc
                .perform(get("/books/genres"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    void shouldReturnCorrectCommentsByBookId() throws Exception {
        Book book = new Book(1L, "Роман", new Author(1L, "Писатель"), new Genre(1L, "Для женщин"));
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(1L, "123", book));
        comments.add(new Comment(2L, "456", book));
        given(commentService.getAll(book.getId())).willReturn(comments);
        List<CommentDto> expectedResult = comments.stream().map(CommentDto::toDto).collect(Collectors.toList());

        mockMvc
                .perform(get("/books/1/comments"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
    }

    @Test
    void shouldCorrectSaveNewComment() throws Exception {
        Book book = new Book(1L, "Роман", new Author(1L, "Писатель"), new Genre(1L, "Для женщин"));
        Comment comment = new Comment(1L, "Хорошая книга", book);
        given(commentService.upsert(0, comment.getDescription(), book.getId())).willReturn(comment);
        String expectedResult = mapper.writeValueAsString(CommentDto.toDto(comment));

        mockMvc.perform(post("/books/1/comments").contentType(APPLICATION_JSON)
                        .content(comment.getDescription()))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));
    }

    @Test
    void shouldCorrectDeleteComment() throws Exception {
        mockMvc.perform(delete("/books/comments/1"))
                .andExpect(status().isOk());
        verify(commentService, times(1)).delete(1L);
    }
*/

