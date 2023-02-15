package ru.otus.poem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.poem.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Query("select u from User u " +
            "join fetch u.roles " +
            "where u.id = :id")
    Optional<User> findById(Long id);
}