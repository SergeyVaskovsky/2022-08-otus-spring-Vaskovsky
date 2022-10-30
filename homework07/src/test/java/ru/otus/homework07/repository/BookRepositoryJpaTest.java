package ru.otus.homework07.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.homework07.model.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookRepositoryJpaTest {

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("Найти книгу в БД")
    @Test
    void shouldFindBookById() {
        Book book = bookRepository.findById(1L).orElse(null);
        assertThat(book).isNotNull();
        assertThat(book.getName()).isEqualTo("Преступление и наказание");
        assertThat(book.getAuthor().getName()).isEqualTo("Достаевский Ф. М.");
        assertThat(book.getGenre().getName()).isEqualTo("Триллер");
    }

    @DisplayName("Найти все книги в БД")
    @Test
    void shouldFindAllBook() {
        List<Book> books = bookRepository.findAll();
        assertThat(books.size()).isEqualTo(3);
    }
}
