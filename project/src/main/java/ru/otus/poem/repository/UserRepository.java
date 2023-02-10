package ru.otus.poem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.poem.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}