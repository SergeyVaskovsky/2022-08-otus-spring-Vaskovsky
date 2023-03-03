package ru.otus.poem.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Payload;
import ru.otus.poem.model.Comment;

@MessagingGateway
public interface ModeratorActivityGateway {

    @Gateway(requestChannel = "newCommentChannel")
    void processActivity(@Payload Comment comment);
}
