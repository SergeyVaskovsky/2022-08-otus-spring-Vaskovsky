package ru.otus.homework05.dao;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Component;
import ru.otus.homework05.exception.AuthorNotFoundException;
import ru.otus.homework05.exception.BookNotFoundException;
import ru.otus.homework05.exception.GenreNotFoundException;
import ru.otus.homework05.model.Author;
import ru.otus.homework05.model.Book;
import ru.otus.homework05.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final JdbcOperations jdbc;
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public long count() {
        Long count = jdbc.queryForObject("select count(*) from book", Long.class);
        return count == null ? 0 : count;
    }

    @Override
    public Book insert(Book book) {
        val author = book.getAuthor();
        if (isNull(author)) {
            throw new AuthorNotFoundException("Author not found");
        }
        val genre = book.getGenre();
        if (isNull(genre)) {
            throw new GenreNotFoundException("Genre not found");
        }
        namedParameterJdbcOperations.update(
                "insert into book (name, author_id, genre_id) " +
                        "values (:name, :author_id, :genre_id)",
                Map.of("name", book.getName(),
                        "author_id", author.getId(),
                        "genre_id", genre.getId()));
        return book;
    }

    @Override
    public void update(Book book) {
        val author = book.getAuthor();
        if (isNull(author)) {
            throw new AuthorNotFoundException("Author not found");
        }
        val genre = book.getGenre();
        if (isNull(genre)) {
            throw new GenreNotFoundException("Genre not found");
        }
        namedParameterJdbcOperations.update(
                "update book set name = :name, author_id = :author_id, genre_id = :genre_id " +
                        "where id = :id",
                Map.of(
                        "id", book.getId(),
                        "name", book.getName(),
                        "author_id", author.getId(),
                        "genre_id", genre.getId()));
    }

    @Override
    public Book getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        List<Book> books = namedParameterJdbcOperations.query(
                "select b.id, b.name, " +
                        "a.id as author_id, a.name as author_name, " +
                        "g.id as genre_id, g.name as genre_name " +
                        "from book b " +
                        "join author a on a.id = b.author_id " +
                        "join genre g on g.id = b.genre_id " +
                        "where b.id = :id", params, new BookMapper());
        if (books == null || books.size() <= 0) {
            throw new BookNotFoundException(String.format("Book with id = %d not found", id));
        }
        return books.get(0);
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select b.id, b.name, " +
                        "a.id as author_id, a.name as author_name, " +
                        "g.id as genre_id, g.name as genre_name " +
                        "from book b " +
                        "join author a on a.id = b.author_id " +
                        "join genre g on g.id = b.genre_id ",
                new BookResultSetExtractor());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from book where id = :id", params
        );
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            Author author = new Author(resultSet.getLong("author_id"), resultSet.getString("author_name"));
            Genre genre = new Genre(resultSet.getLong("genre_id"), resultSet.getString("genre_name"));
            return new Book(id, name, author, genre);
        }
    }
}
