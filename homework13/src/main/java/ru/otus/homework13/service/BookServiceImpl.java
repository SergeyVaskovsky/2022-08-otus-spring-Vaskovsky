package ru.otus.homework13.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.stereotype.Service;
import ru.otus.homework13.exception.BookNotFoundException;
import ru.otus.homework13.model.Book;
import ru.otus.homework13.repository.BookRepository;

import javax.transaction.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookRepository bookRepository;
    private final CommentService commentService;
    private final MutableAclService mutableAclService;

    @Override
    @PostAuthorize("@securityFilterServiceImpl.filter(authentication, returnObject)")
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    @PostAuthorize("@securityFilterServiceImpl.filter(authentication, returnObject)")
    public Book getById(long bookId) {
        Book book = bookRepository.findById(bookId);
        if (book == null) {
            throw new BookNotFoundException(String.format("Book with id = %d not found", bookId));
        }
        return book;
    }

    @Transactional
    @Override
    @PreAuthorize("@securityFilterServiceImpl.filter(authentication, #book)")
    public Book upsert(Book book) {
        Book savedBook = bookRepository.save(book);
        return savedBook;
    }

    @Transactional
    @Override
    @PreAuthorize("@securityFilterServiceImpl.filter(authentication, #book)")
    public void delete(Book book) {
        commentService.deleteAll(commentService.getAll(book.getId()));
        bookRepository.delete(bookRepository.findById(book.getId()));
    }

}
