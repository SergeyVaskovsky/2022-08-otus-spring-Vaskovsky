package ru.otus.homework05;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework05.dao.AuthorDaoJdbc;
import ru.otus.homework05.dao.BookDaoJdbc;
import ru.otus.homework05.dao.GenreDaoJdbc;
import ru.otus.homework05.model.Author;
import ru.otus.homework05.model.Book;
import ru.otus.homework05.model.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import({BookDaoJdbc.class, AuthorDaoJdbc.class, GenreDaoJdbc.class})
public class BookDaoJdbcTest {

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    @Autowired
    private GenreDaoJdbc genreDaoJdbc;

    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @DisplayName("Найти книгу в БД")
    @Test
    void shouldFindBookById() {

    }


    @DisplayName("Добавить книгу в БД")
    @Test
    void shouldInsertBook() {
        Author author = authorDaoJdbc.getById(1L);
        Genre genre = genreDaoJdbc.getById(1L);
        bookDaoJdbc.deleteById(1L);
        Book expectedBook = new Book(1L, "Преступление и наказание", author, genre);
        bookDaoJdbc.insert(expectedBook);
        Book actualBook = bookDaoJdbc.getById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Изменить книгу в БД")
    @Test
    void shouldUpdateBook() {
        Author authorOld = authorDaoJdbc.getById(1L);
        Genre genreOld = genreDaoJdbc.getById(1L);
        Book expectedBook = new Book(1L, "Преступление и наказание", authorOld, genreOld);
        bookDaoJdbc.getById(expectedBook.getId());
        bookDaoJdbc.insert(expectedBook);

        Author authorNew = authorDaoJdbc.getById(2L);
        Genre genreNew = genreDaoJdbc.getById(2L);


        Book expectedBook = new Book(1L, "Преступление и наказание", author, genre);
        bookDaoJdbc.insert(expectedBook);
        Book actualBook = bookDaoJdbc.getById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

}
