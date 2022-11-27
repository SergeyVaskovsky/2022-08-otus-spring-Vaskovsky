package ru.otus.homework11.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework11.model.Comment;
import ru.otus.homework11.repository.CommentRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookCommentService bookService;

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
        commentRepository.deleteById(commentId);
    }

    @Override
    public void deleteAll(Iterable<Comment> comments) {
        commentRepository.deleteAll(comments);
    }
}
