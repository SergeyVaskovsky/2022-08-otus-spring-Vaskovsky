package ru.otus.homework14.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework14.model.rdb.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
