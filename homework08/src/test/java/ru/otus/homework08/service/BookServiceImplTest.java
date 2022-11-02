package ru.otus.homework08.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.homework08.model.Comment;
import ru.otus.homework08.repository.BookRepository;
import ru.otus.homework08.model.Author;
import ru.otus.homework08.model.Book;
import ru.otus.homework08.model.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false",
        classes = {BookServiceImpl.class})
public class BookServiceImplTest {

    @Autowired
    private BookServiceImpl bookServiceImpl;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private DeleteCommentServiceImpl deleteCommentService;

    @Test
    void shouldReturnAllBooks() {
        when(bookRepository.findAll()).thenReturn(
                List.of(
                        new Book("1", "Роман", new Author("1", "Писатель"), new Genre("1", "Для женщин")),
                        new Book("2", "Повесть", new Author("2", "Писатель 2"), new Genre("2", "Беллитристика")),
                        new Book("3", "Статья", new Author("3", "Ученый"), new Genre("1", "Наука"))
                )
        );
        List<Book> books = bookServiceImpl.getAll();
        assertThat(books.size()).isEqualTo(3);
    }

    @Test
    void shouldInsertBook() {
        Book book = new Book("0", "Котики", new Author("1", "Ученый"), new Genre("3", "Наука"));
        List<Book> books = new ArrayList<>();
        books.add(new Book("1", "Роман", new Author("1", "Писатель"), new Genre("1", "Для женщин")));
        books.add(new Book("2", "Повесть", new Author("2", "Писатель 2"), new Genre("2", "Беллитристика")));
        books.add(new Book("3", "Статья", new Author("3", "Ученый"), new Genre("3", "Наука")));

        when(bookRepository.findAll()).thenReturn(books);
        when(bookRepository.save(book)).thenAnswer(a -> {
            books.add(book);
            return a.getArgument(0);
        });

        bookServiceImpl.upsert("0", "Котики", "1", "1");
        Book actualBook = bookServiceImpl
                .getAll()
                .stream()
                .filter(b -> "0".equals(b.getId()))
                .findFirst()
                .orElse(null);
        assertThat(actualBook).isNotNull();
        assertThat(actualBook.getId()).isEqualTo("0");
    }

    @Test
    void shouldDeleteBook() {
        Book bookToDelete = new Book("1", "Роман", new Author("1", "Писатель"), new Genre("1", "Для женщин"));
        List<Book> books = new ArrayList<>();
        books.add(bookToDelete);
        books.add(new Book("2", "Повесть", new Author("2", "Писатель 2"), new Genre("2", "Беллитристика")));
        books.add(new Book("3", "Статья", new Author("3", "Ученый"), new Genre("3", "Наука")));

        Comment good = new Comment("1", "Хорошо", bookToDelete);
        Comment bad = new Comment("2", "Плохо", bookToDelete);

        List<Comment> comments = new ArrayList<>();
        comments.add(good);
        comments.add(bad);

        when(bookRepository.findAll()).thenReturn(books);
        when(bookRepository.findById(bookToDelete.getId())).thenReturn(Optional.of(bookToDelete));

        doAnswer(invocation -> {
            books.remove(bookToDelete);
            return null;
        }).when(bookRepository).deleteById(bookToDelete.getId());

        doAnswer(invocation -> {
            comments.clear();
            return null;
        }).when(deleteCommentService).deleteAllByBookId(bookToDelete.getId());

        assertThat(comments.size()).isEqualTo(2);

        bookServiceImpl.delete(bookToDelete.getId());
        assertThat(bookServiceImpl
                .getAll()
                .stream()
                .anyMatch(b -> Objects.equals(b.getId(), bookToDelete.getId()))).isEqualTo(false);
        assertThat(comments.size()).isEqualTo(0);
    }

    @Test
    void shouldUpdateBook() {
        Book bookToUpdate = new Book("1", "Роман", new Author("1", "Писатель"), new Genre("1", "Для женщин"));
        List<Book> books = new ArrayList<>();
        books.add(bookToUpdate);
        books.add(new Book("2", "Повесть", new Author("2", "Писатель 2"), new Genre("2", "Беллитристика")));
        books.add(new Book("3", "Статья", new Author("3", "Ученый"), new Genre("3", "Наука")));

        when(bookRepository.findAll()).thenReturn(books);
        doAnswer(invocation -> {
            bookToUpdate.setName("Хроники");
            bookToUpdate.setAuthor(new Author("3", "Ученый"));
            bookToUpdate.setGenre(new Genre("3", "Наука"));
            return null;
        }).when(bookRepository).save(bookToUpdate);

        bookServiceImpl.upsert("1", "Хроники", "3", "3");
        assertThat(bookServiceImpl
                .getAll()
                .stream()
                .anyMatch(b -> Objects.equals(b.getId(), "1") &&
                        "Хроники".equals(b.getName()) &&
                        "Ученый".equals(b.getAuthor().getName()) &&
                        "Наука".equals(b.getGenre().getName())
                )).isEqualTo(true);
    }
}