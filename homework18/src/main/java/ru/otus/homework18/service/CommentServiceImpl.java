package ru.otus.homework18.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework18.exception.CommentNotDeletedException;
import ru.otus.homework18.model.Author;
import ru.otus.homework18.model.Book;
import ru.otus.homework18.model.Comment;
import ru.otus.homework18.model.Genre;
import ru.otus.homework18.repository.CommentRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookCommentService bookService;
    private final RandomTimeoutService randomTimeoutService;

    @HystrixCommand(fallbackMethod = "buildFallbackComments")
    @Override
    public List<Comment> getAll(long bookId) {
        randomTimeoutService.sleepRandomTimeout();
        return commentRepository.findAllByBookId(bookId);
    }

    public List<Comment> buildFallbackComments(long bookId) {
        Comment comment = getEmptyComment();
        List<Comment> comments = List.of(comment);
        return comments;
    }

    @Override
    @Transactional
    public Comment upsert(long commentId, String description, long bookId) {
        Comment comment = new Comment(commentId, description, bookService.getById(bookId));
        commentRepository.save(comment);
        return comment;
    }

    @HystrixCommand(fallbackMethod = "buildFallbackDelete")
    @Override
    public void delete(long commentId) {
        randomTimeoutService.sleepRandomTimeout();
        commentRepository.deleteById(commentId);
    }

    public void buildFallbackDelete(long commentId) {
        throw new CommentNotDeletedException();
    }

    @Override
    public void deleteAllByBookId(long bookId) {
        commentRepository.deleteAllByBookId(bookId);
    }

    @Override
    public Comment getEmptyComment() {
        Comment comment = new Comment();
        comment.setId(0L);
        comment.setDescription("По техническим причинам комментарии недоступны");
        Book book = new Book();
        book.setId(0L);
        book.setName("По техническим причинам список книг недоступен");
        book.setAuthor(new Author(0L, "Н/Д"));
        book.setGenre(new Genre(0L, "Н/Д"));
        comment.setBook(book);
        return comment;
    }
}
