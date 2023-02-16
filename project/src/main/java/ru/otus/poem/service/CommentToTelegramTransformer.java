package ru.otus.poem.service;

import ru.otus.poem.model.Comment;
import ru.otus.poem.model.dto.TelegramMessage;

public interface CommentToTelegramTransformer {
    TelegramMessage transform(Comment comment);
}
