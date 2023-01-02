package ru.otus.homework13.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework13.exception.BookNotFoundException;
import ru.otus.homework13.model.Book;
import ru.otus.homework13.repository.BookRepository;

@Service
@RequiredArgsConstructor
public class BookCommentServiceImpl implements BookCommentService {

    private final BookRepository bookRepository;

    @Override
    public Book getById(long bookId) {
        Book book = bookRepository.findById(bookId);
        if (book == null) {
            throw new BookNotFoundException(String.format("Book with id = %d not found", bookId));
        }
        return book;
    }
}
