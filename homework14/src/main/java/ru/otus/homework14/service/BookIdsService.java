package ru.otus.homework14.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class BookIdsService {
    private final Map<String, Long> ids = new ConcurrentHashMap<>();

    public Map<String, Long> getIds() {
        return ids;
    }
}
