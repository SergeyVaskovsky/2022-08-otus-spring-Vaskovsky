package ru.otus.homework08.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework08.repository.CommentRepository;
import ru.otus.homework08.model.Comment;

import java.util.List;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookService bookService;

    @Override
    public List<Comment> getAll(String bookId) {
        return commentRepository.findAllByBookId(bookId);
    }

    @Override
    public Comment upsert(String commentId, String description, String bookId) {
        Comment comment;
        if ("".equals(commentId)) {
            comment = new Comment(description, bookService.getById(bookId));
        } else {
            comment = new Comment(commentId, description, bookService.getById(bookId));
        }
        commentRepository.save(comment);
        return comment;
    }
}
