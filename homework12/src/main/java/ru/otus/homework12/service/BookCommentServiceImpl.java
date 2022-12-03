package ru.otus.homework12.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework12.exception.BookNotFoundException;
import ru.otus.homework12.model.Book;
import ru.otus.homework12.repository.BookRepository;

@Service
@RequiredArgsConstructor
public class BookCommentServiceImpl implements BookCommentService {

    private final BookRepository bookRepository;

    @Override
    public Book getById(long bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(
                String.format("Book with id = %d not found", bookId)
        ));
    }
}
