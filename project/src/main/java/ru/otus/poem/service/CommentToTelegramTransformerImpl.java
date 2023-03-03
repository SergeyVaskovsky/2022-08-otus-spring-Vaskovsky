package ru.otus.poem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.poem.config.AppProps;
import ru.otus.poem.model.Comment;
import ru.otus.poem.model.dto.TelegramMessage;

@RequiredArgsConstructor
@Service
public class CommentToTelegramTransformerImpl implements CommentToTelegramTransformer {

    private final AppProps appProps;

    @Override
    public TelegramMessage transform(Comment comment) {
        TelegramMessage message = new TelegramMessage(
                comment.getId(),
                appProps.getServerAddress(),
                comment.getText()
        );

        return message;
    }
}
