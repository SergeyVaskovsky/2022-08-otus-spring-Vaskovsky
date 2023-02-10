package ru.otus.homework18.service;

import ru.otus.homework18.model.Comment;

public interface CallTransactionalMethodService {
    void delete(long bookId);

    Comment upsert(long commentId, String description, long bookId);
}
