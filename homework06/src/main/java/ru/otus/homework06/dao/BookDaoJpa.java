package ru.otus.homework06.dao;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Repository;
import ru.otus.homework06.exception.AuthorNotFoundException;
import ru.otus.homework06.exception.BookNotFoundException;
import ru.otus.homework06.exception.GenreNotFoundException;
import ru.otus.homework06.model.Author;
import ru.otus.homework06.model.Book;
import ru.otus.homework06.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.isNull;

@Repository
@RequiredArgsConstructor
public class BookDaoJpa implements BookDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery("select b from Book b",
                Book.class);
        return query.getResultList();
    }

    @Override
    public void delete(Book book) {
        em.remove(book);
    }
}
