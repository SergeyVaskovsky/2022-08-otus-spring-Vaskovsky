package ru.otus.homework06.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework06.model.Author;
import ru.otus.homework06.model.Book;
import ru.otus.homework06.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({BookDaoJpa.class})
public class BookDaoJpaTest {
    @Autowired
    private BookDaoJpa bookDaoJpa;

    @DisplayName("Найти книгу в БД")
    @Test
    void shouldFindBookById() {
        Book book = bookDaoJpa.findById(1L).orElse(null);
        assertThat(book).isNotNull();
        assertThat(book.getName()).isEqualTo("Преступление и наказание");
        assertThat(book.getAuthor().getName()).isEqualTo("Достаевский Ф. М.");
        assertThat(book.getGenre().getName()).isEqualTo("Триллер");
    }

    @DisplayName("Найти все книги в БД")
    @Test
    void shouldFindAllBook() {
        List<Book> books = bookDaoJpa.findAll();
        assertThat(books.size()).isEqualTo(3);
    }

    @DisplayName("Добавить книгу в БД")
    @Test
    void shouldInsertBook() {
        Author author = new Author(2L, "Коллектив авторов");
        Genre genre = new Genre(2L, "Научпоп");
        Book expectedBook = new Book(4L, "Дельфины и люди", author, genre);
        bookDaoJpa.save(expectedBook);
        Book actualBook = bookDaoJpa.findById(expectedBook.getId()).orElse(null);
        assertThat(actualBook).isNotNull();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
        bookDaoJpa.delete(expectedBook);
    }

    @DisplayName("Изменить книгу в БД")
    @Test
    void shouldUpdateBook() {
        Author authorOld = new Author(1L, "Достаевский Ф. М.");
        Genre genreOld = new Genre(1L, "Триллер");

        Book expectedBook = new Book(1L, "Преступление и наказание", authorOld, genreOld);

        Author authorNew = new Author(2L, "Коллектив авторов");
        Genre genreNew = new Genre(2L, "Научпоп");

        expectedBook.setName("Преступление и наказание 2");
        expectedBook.setAuthor(authorNew);
        expectedBook.setGenre(genreNew);

        bookDaoJpa.save(expectedBook);

        Book actualBook = bookDaoJpa.findById(expectedBook.getId()).orElse(null);
        assertThat(actualBook).isNotNull();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Удалить книгу из БД")
    @Test
    void shouldDeleteBook() {
        Author author = new Author(2L, "Коллектив авторов");
        Genre genre = new Genre(2L, "Научпоп");
        Book expectedBook = new Book(5L, "Дельфины и акулы", author, genre);
        bookDaoJpa.save(expectedBook);
        bookDaoJpa.delete(expectedBook);
        assertThat(bookDaoJpa.findById(5L).orElse(null)).isNull();
    }

}
