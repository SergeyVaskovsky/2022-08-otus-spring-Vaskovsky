package ru.otus.homework05.dao;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import ru.otus.homework05.exception.AuthorNotFoundException;
import ru.otus.homework05.exception.GenreNotFoundException;
import ru.otus.homework05.model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final JdbcOperations jdbc;
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    private final AuthorDao authorDao;
    private final GenreDao genreDao;

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
                "insert into book (id, name, author_id, genre_id) " +
                        "values (:id, :name, :author_id, :genre_id)",
                Map.of(
                        "id", book.getId(),
                        "name", book.getName(),
                        "author_id", authorId.getId(),
                        "genre_id", genreId.getId()));
    }

    @Override
    public Book getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject(
                "select id, name, author_id, genre_id from book where id = :id", params, new BookMapper(authorDao, genreDao)
        );
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select id, name, author_id, genre_id from book", new BookMapper(authorDao, genreDao));
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from book where id = :id", params
        );
    }

    private static class BookMapper implements RowMapper<Book> {
        private final AuthorDao authorDao;
        private final GenreDao genreDao;

        private BookMapper(AuthorDao authorDao, GenreDao genreDao) {
            this.authorDao = authorDao;
            this.genreDao = genreDao;
        }

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Book(
                    id,
                    name,
                    authorDao.getById(resultSet.getLong("author_id")),
                    genreDao.getById(resultSet.getLong("genre_id")));
        }
    }
}
