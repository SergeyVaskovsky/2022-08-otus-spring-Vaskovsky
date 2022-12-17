package ru.otus.homework13.service;

import org.springframework.security.core.Authentication;
import ru.otus.homework13.model.Book;

import java.util.List;

public interface SecurityFilterService {
    boolean filter(Authentication authentication, List<Book> domain);
}
