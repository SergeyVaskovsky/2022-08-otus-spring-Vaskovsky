package ru.otus.homework15.service;

import org.springframework.integration.annotation.Splitter;
import org.springframework.stereotype.Component;
import ru.otus.homework15.model.Order;
import ru.otus.homework15.model.Part;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderSplitter {

    @Splitter(inputChannel = "inputChannel")
    public Collection splitItem(Order order) {
        List messages = new ArrayList();

        Part part = new Part(Part.Descriptor.CAR_BODY_NAME, order.getBodyName());
        messages.add(part);

        part = new Part(Part.Descriptor.ENGINE, String.valueOf(order.getEngine()));
        messages.add(part);

        part = new Part(Part.Descriptor.OPTIONS, order.getOptions().stream().collect(Collectors.joining(", ")));
        messages.add(part);

        return messages;
    }
}