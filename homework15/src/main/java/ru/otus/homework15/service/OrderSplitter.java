package ru.otus.homework15.service;


import org.springframework.integration.annotation.Splitter;
import org.springframework.stereotype.Component;
import ru.otus.homework15.model.Order;
import ru.otus.homework15.model.Part;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class OrderSplitter {

    @Splitter
    public Collection<Part> splitItem(Order order) {
        List<Part> messages = new ArrayList<>();

        Part part = new Part(Part.Descriptor.CAR_BODY_NAME, order.getBodyName());
        messages.add(part);

        part = new Part(Part.Descriptor.ENGINE, String.valueOf(order.getEngine()));
        messages.add(part);

        part = new Part(Part.Descriptor.OPTIONS, String.join(", ", order.getOptions()));
        messages.add(part);

        return messages;
    }
}