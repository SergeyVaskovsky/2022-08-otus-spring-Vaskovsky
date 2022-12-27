package ru.otus.homework15.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.homework15.model.Butterfly;
import ru.otus.homework15.model.Caterpillar;

@MessagingGateway
public interface Nature {
    @Gateway(requestChannel = "inputChannel", replyChannel = "outputChannel")
    Butterfly process(Caterpillar caterpillar);
}