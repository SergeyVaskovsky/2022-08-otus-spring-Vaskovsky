package ru.otus.homework15.service;

import org.springframework.stereotype.Service;
import ru.otus.homework15.model.CarBody;
import ru.otus.homework15.model.Order;
import ru.otus.homework15.model.Part;

@Service
public class CarBodyService {
    public CarBody produce(Part part) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("Car body constructed");
        return new CarBody(part.getValue());
    }
}
