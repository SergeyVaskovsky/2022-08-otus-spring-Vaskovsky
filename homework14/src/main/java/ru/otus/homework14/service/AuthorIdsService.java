package ru.otus.homework14.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthorIdsService {
    private final Map<String, Long> ids = new ConcurrentHashMap<>();

    public Map<String, Long> getIds() {
        return ids;
    }
}
