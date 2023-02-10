package ru.otus.homework18.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class AbstractCacheRepositoryImpl<T> implements CacheRepository<T> {
    private final ConcurrentHashMap<Long, T> storage = new ConcurrentHashMap<>();

    @Override
    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public T save(Long id, T object) {
        return storage.put(id, object);
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }

    @Override
    public void delete(Long id) {
        storage.remove(id);
    }
}

