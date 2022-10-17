package ru.otus.homework06.dao;

import org.springframework.stereotype.Repository;
import ru.otus.homework06.model.Book;
import ru.otus.homework06.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentDaoJpa implements CommentDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == 0) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public List<Comment> findAll(Book book) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c " +
                        "join fetch c.book b " +
                        "join fetch b.author " +
                        "join fetch b.genre " +
                        "where c.book.id = :bookId",
                Comment.class);
        query.setParameter("bookId", book.getId());
        return query.getResultList();
    }

    @Override
    public void delete(Comment comment) {
        em.remove(em.contains(comment) ? comment : em.merge(comment));
    }
}
