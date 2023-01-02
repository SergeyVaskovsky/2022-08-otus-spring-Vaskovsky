package ru.otus.homework15.service;

import org.springframework.stereotype.Service;
import ru.otus.homework15.model.Engine;
import ru.otus.homework15.model.Order;
import ru.otus.homework15.model.Part;

@Service
public class EngineService {
    public Engine produce(Part part) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("Engine constructed");
        return new Engine(Float.parseFloat(part.getValue()));
    }
}
