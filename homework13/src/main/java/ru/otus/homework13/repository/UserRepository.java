package ru.otus.homework13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework13.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}