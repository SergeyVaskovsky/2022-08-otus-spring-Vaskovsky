package ru.otus.homework06.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.homework06.model.Book;
import ru.otus.homework06.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentDaoJpa implements CommentDao {

    private final EntityManager em;

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
    public List<Comment> findAll(long bookId) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c " +
                        "where c.book.id = :bookId",
                Comment.class);
        query.setParameter("bookId", bookId);
        List<Comment> result = query.getResultList();
        Book book = em.find(Book.class, bookId);
        result.forEach(comment -> comment.setBook(book));
        return result;
    }

    @Override
    public void delete(Comment comment) {
        em.remove(em.contains(comment) ? comment : em.find(Comment.class, comment.getId()));
    }
}
