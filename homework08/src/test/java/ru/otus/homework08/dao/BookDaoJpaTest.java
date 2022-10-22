package ru.otus.homework08.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.homework08.model.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class BookDaoJpaTest {

    @Autowired
    private BookDao bookDao;

    @DisplayName("Найти книгу в БД")
    @Test
    void shouldFindBookById() {
        Book book = bookDao.findById(1L).orElse(null);
        assertThat(book).isNotNull();
        assertThat(book.getName()).isEqualTo("Фантастика и обычные люди");
        assertThat(book.getAuthor().getName()).isEqualTo("Кайт Том");
        assertThat(book.getGenre().getName()).isEqualTo("Фантастика");
    }

    @DisplayName("Найти все книги в БД")
    @Test
    void shouldFindAllBook() {
        List<Book> books = bookDao.findAll();
        assertThat(books.size()).isEqualTo(3);
    }
}
