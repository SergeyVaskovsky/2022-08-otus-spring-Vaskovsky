package ru.otus.homework08.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework08.model.Comment;
import ru.otus.homework08.repository.CommentRepository;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class DeleteCommentServiceImpl implements DeleteCommentService{

    private final CommentRepository commentRepository;
    @Override
    public void delete(String commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (isNull(comment)) {
            return;
        }
        commentRepository.delete(comment);
    }

    @Override
    public void deleteAllByBookId(String bookId) {
        commentRepository.deleteAllByBookId(bookId);
    }
}
