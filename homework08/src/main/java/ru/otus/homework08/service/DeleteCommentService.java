package ru.otus.homework08.service;

public interface DeleteCommentService {

    void delete(String commentId);

    void deleteAllByBookId(String bookId);
}
