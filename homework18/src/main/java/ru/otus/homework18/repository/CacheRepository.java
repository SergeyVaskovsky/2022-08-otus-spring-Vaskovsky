package ru.otus.homework18.repository;

import java.util.List;

public interface CacheRepository<T> {
    List<T> findAll();

    T save(Long id, T object);

    void deleteAll();

    void delete(Long id);
}
