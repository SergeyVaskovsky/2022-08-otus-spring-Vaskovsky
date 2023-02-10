package ru.otus.homework18.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework18.exception.BookNotDeletedException;
import ru.otus.homework18.model.Comment;

@Service
@RequiredArgsConstructor
public class CallTransactionalMethodServiceImpl implements CallTransactionalMethodService {

    private final BookService bookService;
    private final CommentService commentService;
    private final RandomTimeoutService randomTimeoutService;

    @HystrixCommand(fallbackMethod = "buildFallbackDelete")
    @Override
    public void delete(long bookId) {
        randomTimeoutService.sleepRandomTimeout();
        bookService.delete(bookId);
    }

    public void buildFallbackDelete(long bookId) {
        throw new BookNotDeletedException();
    }

    @HystrixCommand(fallbackMethod = "buildFallbackUpsert")
    @Override
    public Comment upsert(long commentId, String description, long bookId) {
        randomTimeoutService.sleepRandomTimeout();
        return commentService.upsert(commentId, description, bookId);
    }

    public Comment buildFallbackUpsert(long commentId, String description, long bookId) {
        Comment comment = commentService.getEmptyComment();
        return comment;
    }
}
