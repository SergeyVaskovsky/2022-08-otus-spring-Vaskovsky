package ru.otus.homework05.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework05.exception.BookNotFoundException;
import ru.otus.homework05.model.Author;
import ru.otus.homework05.model.Book;
import ru.otus.homework05.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
@Import({BookDaoJdbc.class})
public class BookDaoJdbcTest {
    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @DisplayName("Найти книгу в БД")
    @Test
    void shouldFindBookById() {
        Book book = bookDaoJdbc.getById(1);
        assertThat(book.getName()).isEqualTo("Преступление и наказание");
        assertThat(book.getAuthor().getName()).isEqualTo("Достаевский Ф. М.");
        assertThat(book.getGenre().getName()).isEqualTo("Триллер");
    }

    @DisplayName("Найти все книги в БД")
    @Test
    void shouldFindAllBook() {
        List<Book> books = bookDaoJdbc.getAll();
        assertThat(books.size()).isEqualTo(3);
    }

    @DisplayName("Найти количество книг в БД")
    @Test
    void shouldReturnCountBooks() {
        assertThat(bookDaoJdbc.count()).isEqualTo(3);
    }

    @DisplayName("Добавить книгу в БД")
    @Test
    void shouldInsertBook() {
        Author author = new Author(2L, "Коллектив авторов");
        Genre genre = new Genre(2L, "Научпоп");
        Book expectedBook = new Book(4L, "Дельфины и люди", author, genre);
        bookDaoJdbc.insert(expectedBook);
        Book actualBook = bookDaoJdbc.getById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
        bookDaoJdbc.deleteById(4L);
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

        bookDaoJdbc.update(expectedBook);

        Book actualBook = bookDaoJdbc.getById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Удалить книгу из БД")
    @Test
    void shouldDeleteBook() {
        Author author = new Author(2L, "Коллектив авторов");
        Genre genre = new Genre(2L, "Научпоп");
        Book expectedBook = new Book(5L, "Дельфины и акулы", author, genre);
        bookDaoJdbc.insert(expectedBook);
        bookDaoJdbc.deleteById(5L);

        assertThatThrownBy(() -> bookDaoJdbc.getById(5L))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessageContaining(String.format("Book with id = %d not found", 5L));
    }

}
