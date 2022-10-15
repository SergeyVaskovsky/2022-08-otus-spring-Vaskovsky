package ru.otus.homework05.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.homework05.model.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
public class BookServiceImplTest {

    @Autowired
    private BookServiceImpl bookServiceImpl;

    @Test
    void shouldReturnAllBooks() {
        List<Book> books = bookServiceImpl.getAll();
        assertThat(books.size()).isEqualTo(3);
    }

    @Test
    void shouldInsertBook() {
        bookServiceImpl.insert(4L, "Котики", 1L, 1L);
        Book book = bookServiceImpl
                .getAll()
                .stream()
                .filter(b -> b.getId() == 4L)
                .findFirst()
                .orElse(null);
        assertThat(book).isNotNull();
        assertThat(book.getId()).isEqualTo(4L);
    }

    @Test
    void shouldDeleteBook() {
        bookServiceImpl.insert(5L, "Пёсики", 1L, 1L);
        bookServiceImpl.delete(5L);
        assertThat(bookServiceImpl
                .getAll()
                .stream()
                .anyMatch(b -> b.getId() == 5L)).isEqualTo(false);
    }

    @Test
    void shouldUpdateBook() {
        bookServiceImpl.insert(6L, "Часы", 2L, 2L);
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
