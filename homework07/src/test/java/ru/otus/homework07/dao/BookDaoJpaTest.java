package ru.otus.homework07.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework07.model.Author;
import ru.otus.homework07.model.Book;
import ru.otus.homework07.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({BookDaoJpa.class})
public class BookDaoJpaTest {

    @Autowired
    private TestEntityManager em;
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
        Book expectedBook = new Book(0L, "Дельфины и люди", author, genre);
        bookDaoJpa.save(expectedBook);
        Book actualBook = em.find(Book.class, expectedBook.getId());
        assertThat(actualBook).isNotNull();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
        em.remove(expectedBook);
    }

    @DisplayName("Изменить книгу в БД")
    @Test
    void shouldUpdateBook() {
        Author authorOld = new Author(1L, "Достаевский Ф. М.");
        Genre genreOld = new Genre(1L, "Триллер");

        Book expectedBook = new Book(0L, "Преступление и наказание", authorOld, genreOld);
        em.persist(expectedBook);
        Author authorNew = new Author(2L, "Коллектив авторов");
        Genre genreNew = new Genre(2L, "Научпоп");

        expectedBook.setName("Преступление и наказание 2");
        expectedBook.setAuthor(authorNew);
        expectedBook.setGenre(genreNew);

        bookDaoJpa.save(expectedBook);

        Book actualBook = em.find(Book.class, expectedBook.getId());
        assertThat(actualBook).isNotNull();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Удалить книгу из БД")
    @Test
    void shouldDeleteBook() {
        Author author = new Author(2L, "Коллектив авторов");
        Genre genre = new Genre(2L, "Научпоп");
        Book expectedBook = new Book(0L, "Дельфины и акулы", author, genre);
        em.persist(expectedBook);
        bookDaoJpa.delete(expectedBook);
        assertThat(em.find(Book.class, expectedBook.getId())).isNull();
    }
}
