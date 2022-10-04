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

}
