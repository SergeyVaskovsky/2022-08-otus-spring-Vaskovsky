package ru.otus.homework13.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.otus.homework13.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @PreAuthorize("@securityFilterServiceImpl.filter(authentication, #book)")
    Book save(@Param("book") Book book);

    @PostAuthorize("@securityFilterServiceImpl.filter(authentication, returnObject)")
    @EntityGraph(attributePaths = {"author", "genre"})
    Book findById(long id);

    @PostAuthorize("@securityFilterServiceImpl.filter(authentication, returnObject)")
    @EntityGraph(attributePaths = {"author", "genre"})
    List<Book> findAll();

    @PreAuthorize("@securityFilterServiceImpl.filter(authentication, #book)")
    void delete(Book book);

}
