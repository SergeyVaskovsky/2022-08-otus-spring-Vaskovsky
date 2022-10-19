package ru.otus.homework05.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.homework05.dao.BookDaoJdbc;
import ru.otus.homework05.model.Author;
import ru.otus.homework05.model.Book;
import ru.otus.homework05.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
    private BookDaoJdbc bookDaoJdbc;

    @Test
    void shouldReturnAllBooks() {
        when(bookDaoJdbc.getAll()).thenReturn(
                List.of(
                        new Book(1L, "Роман", new Author(1L, "Писатель"), new Genre(1L, "Для женщин")),
                        new Book(2L, "Повесть", new Author(2L, "Писатель 2"), new Genre(2L, "Беллитристика")),
                        new Book(3L, "Статья", new Author(3L, "Ученый"), new Genre(1L, "Наука"))
                )
        );
        List<Book> books = bookServiceImpl.getAll();
        assertThat(books.size()).isEqualTo(3);
    }

    @Test
    void shouldInsertBook() {
        Book book = new Book(4L, "Котики", new Author(1L, "Ученый"), new Genre(1L, "Наука"));
        List<Book> books = List.of(book);
        when(bookDaoJdbc.getAll()).thenReturn(books);
        when(bookDaoJdbc.insert(book)).thenReturn(book);

        bookServiceImpl.insert("Котики", 1L, 1L);
        Book actualBook = bookServiceImpl
                .getAll()
                .stream()
                .filter(b -> b.getId() == 4L)
                .findFirst()
                .orElse(null);
        assertThat(actualBook).isNotNull();
        assertThat(actualBook.getId()).isEqualTo(4L);
    }

    @Test
    void shouldDeleteBook() {
        Book book = new Book(4L, "Пёсики", new Author(1L, "Ученый"), new Genre(1L, "Наука"));
        List<Book> books = List.of(book);
        when(bookDaoJdbc.getAll()).thenReturn(books);
        when(bookDaoJdbc.insert(book)).thenReturn(book);

        bookServiceImpl.insert("Пёсики", 1L, 1L);
        bookServiceImpl.delete(4L);
        assertThat(bookServiceImpl
                .getAll()
                .stream()
                .anyMatch(b -> b.getId() == 4L)).isEqualTo(false);
    }

    @Test
    void shouldUpdateBook() {
        bookServiceImpl.insert("Часы", 2L, 2L);
        bookServiceImpl.update(6L, "Хроники", 1L, 1L);
        assertThat(bookServiceImpl
                .getAll()
                .stream()
                .anyMatch(b -> b.getId() == 6L &&
                        "Хроники".equals(b.getName()) &&
                        "Достаевский Ф. М.".equals(b.getAuthor().getName()) &&
                        "Триллер".equals(b.getGenre().getName())
                )).isEqualTo(true);
        bookServiceImpl.delete(6L);
    }
}
