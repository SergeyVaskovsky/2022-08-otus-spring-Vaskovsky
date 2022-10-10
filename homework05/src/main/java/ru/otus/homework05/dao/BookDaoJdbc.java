package ru.otus.homework05.dao;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import ru.otus.homework05.exception.AuthorNotFoundException;
import ru.otus.homework05.exception.BookNotFoundException;
import ru.otus.homework05.exception.GenreNotFoundException;
import ru.otus.homework05.model.Book;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final static String BOOK_QUERY = "select b.id, b.name, a.author_id, a.author_name, g.genre_id, g.genre_name from book b " +
            "join author a on a.id = b.author_id " +
            "join genre g on g.id = b.genre_id ";

    private final JdbcOperations jdbc;
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public long count() {
        Long count = jdbc.queryForObject("select count(*) from book", Long.class);
        return count == null ? 0 : count;
    }

    @Override
    public void insert(Book book) {
        val authorId = book.getAuthor();
        if (isNull(authorId)) throw new AuthorNotFoundException("Author not found");
        val genreId = book.getGenre();
        if (isNull(genreId)) throw new GenreNotFoundException("Genre not found");
        namedParameterJdbcOperations.update(
                "insert into book (name, author_id, genre_id) " +
                        "values (:name, :author_id, :genre_id)",
                Map.of(
                        "name", book.getName(),
                        "author_id", authorId.getId(),
                        "genre_id", genreId.getId()));
    }

    @Override
    public void update(Book book) {
        val authorId = book.getAuthor();
        if (isNull(authorId)) throw new AuthorNotFoundException("Author not found");
        val genreId = book.getGenre();
        if (isNull(genreId)) throw new GenreNotFoundException("Genre not found");
        namedParameterJdbcOperations.update(
                "update book set name = :name, author_id = :author_id, genre_id = :genre_id " +
                        "where id = :id",
                Map.of(
                        "id", book.getId(),
                        "name", book.getName(),
                        "author_id", authorId.getId(),
                        "genre_id", genreId.getId()));
    }

    @Override
    public Book getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        List<Book> books = namedParameterJdbcOperations.query(
                BOOK_QUERY + " where b.id = :id", params, new BookResultSetExtractor());
        if (books == null || books.size() <= 0) {
            throw new BookNotFoundException(String.format("Book with id = %d not found", id));
        }
        return books.get(0);
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query(BOOK_QUERY, new BookResultSetExtractor());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from book where id = :id", params
        );
    }
}
