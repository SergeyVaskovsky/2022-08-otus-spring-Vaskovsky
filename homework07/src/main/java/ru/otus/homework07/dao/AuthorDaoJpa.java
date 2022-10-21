package ru.otus.homework07.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.homework07.model.Author;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJpa implements AuthorDao {

    private final EntityManager em;

    @Override
    public List<Author> findAll() {
        TypedQuery<Author> query = em.createQuery("select a from Author a",
                Author.class);
        return query.getResultList();
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }
}
