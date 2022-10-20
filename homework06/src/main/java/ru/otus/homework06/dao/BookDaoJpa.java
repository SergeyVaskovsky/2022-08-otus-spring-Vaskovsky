package ru.otus.homework06.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.homework06.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookDaoJpa implements BookDao {

    private final EntityManager em;

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
        TypedQuery<Book> query = em.createQuery("select b from Book b " +
                        "join fetch b.author a " +
                        "join fetch b.genre g",
                Book.class);
        return query.getResultList();
    }

    @Override
    public void delete(Book book) {
        em.remove(em.contains(book) ? book : em.find(Book.class, book.getId()));
    }
}
