package ru.otus.homework18.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework18.exception.BookNotDeletedException;

@Service
@RequiredArgsConstructor
public class DeleteBookServiceImpl implements DeleteBookService {

    private final BookService bookService;
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
}
