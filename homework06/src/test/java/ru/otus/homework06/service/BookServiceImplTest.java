package ru.otus.homework06.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.homework06.model.Book;

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
        Book insertedBook = bookServiceImpl.upsert(0L, "Котики", 1L, 1L);
        Book book = bookServiceImpl
                .getAll()
                .stream()
                .filter(b -> b.getId() == insertedBook.getId())
                .findFirst()
                .orElse(null);
        assertThat(book).isNotNull();
        assertThat(book.getId()).isEqualTo(insertedBook.getId());
    }

    @Test
    void shouldDeleteBook() {
        bookServiceImpl.upsert(5L, "Пёсики", 1L, 1L);
        bookServiceImpl.delete(5L);
        assertThat(bookServiceImpl
                .getAll()
                .stream()
                .anyMatch(b -> b.getId() == 5L)).isEqualTo(false);
    }

    @Test
    void shouldUpdateBook() {
        Book book = bookServiceImpl.upsert(0L, "Часы", 2L, 2L);
        bookServiceImpl.upsert(book.getId(), "Хроники", 1L, 1L);
        assertThat(bookServiceImpl
                .getAll()
                .stream()
                .anyMatch(b -> b.getId() == book.getId() &&
                        "Хроники".equals(b.getName()) &&
                        "Достаевский Ф. М.".equals(b.getAuthor().getName()) &&
                        "Триллер".equals(b.getGenre().getName())
                )).isEqualTo(true);
        bookServiceImpl.delete(book.getId());
    }
}
