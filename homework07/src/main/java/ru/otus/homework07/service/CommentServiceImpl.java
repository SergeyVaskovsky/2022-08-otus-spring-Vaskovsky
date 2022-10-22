package ru.otus.homework07.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework07.dao.CommentDao;
import ru.otus.homework07.model.Comment;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;
    private final BookService bookService;

    @Override
    public List<Comment> getAll(long bookId) {
        return commentDao.findAllByBookId(bookId);
    }

    @Transactional
    @Override
    public Comment upsert(long commentId, String description, long bookId) {
        Comment comment = new Comment(commentId, description, bookService.getById(bookId));
        commentDao.save(comment);
        return comment;
    }

    @Transactional
    @Override
    public void delete(long commentId) {
        Comment comment = commentDao.findById(commentId).orElse(null);
        if (isNull(comment)) {
            return;
        }
        commentDao.delete(comment);
    }
}
