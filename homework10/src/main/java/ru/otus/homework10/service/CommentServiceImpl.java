package ru.otus.homework10.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework10.model.Comment;
import ru.otus.homework10.repository.CommentRepository;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookService bookService;

    @Override
    public List<Comment> getAll(long bookId) {
        return commentRepository.findAllByBookId(bookId);
    }

    @Transactional
    @Override
    public Comment upsert(long commentId, String description, long bookId) {
        Comment comment = new Comment(commentId, description, bookService.getById(bookId));
        commentRepository.save(comment);
        return comment;
    }

    @Transactional
    @Override
    public void delete(long commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (isNull(comment)) {
            return;
        }
        commentRepository.delete(comment);
    }
}
