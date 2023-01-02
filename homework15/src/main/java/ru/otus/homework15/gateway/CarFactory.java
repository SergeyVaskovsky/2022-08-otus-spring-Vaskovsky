package ru.otus.homework15.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.homework15.model.Car;
import ru.otus.homework15.model.Order;

@MessagingGateway
public interface CarFactory {
    @Gateway(requestChannel = "inputChannel", replyChannel = "outputChannel")
    Car process(Order order);
}