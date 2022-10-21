package ru.otus.homework07.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework07.dao.CommentDaoJpa;
import ru.otus.homework07.model.Comment;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentDaoJpa commentDaoJpa;
    private final BookService bookService;

    @Override
    public List<Comment> getAll(long bookId) {
        return commentDaoJpa.findAll(bookId);
    }

    @Transactional
    @Override
    public Comment upsert(long commentId, String description, long bookId) {
        Comment comment = new Comment(commentId, description, bookService.getById(bookId));
        commentDaoJpa.save(comment);
        return comment;
    }

    @Transactional
    @Override
    public void delete(long commentId) {
        Comment comment = commentDaoJpa.findById(commentId).orElse(null);
        if (isNull(comment)) {
            return;
        }
        commentDaoJpa.delete(comment);
    }
}
