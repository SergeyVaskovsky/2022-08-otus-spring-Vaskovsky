package ru.otus.homework14.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IdsService {
    private final Map<String, Long> ids = new ConcurrentHashMap<>();

    public Long getRdbId(String mongoId) {
        return ids.get(mongoId);
    }

    public void putRdbId(String mongoId, Long rdbId) {
        ids.put(mongoId, rdbId);
    }
}
