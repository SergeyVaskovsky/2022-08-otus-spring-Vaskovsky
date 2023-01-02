package ru.otus.homework15.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.homework15.model.CarBody;
import ru.otus.homework15.model.Part;

@Service
@Slf4j
public class CarBodyService {
    public CarBody produce(Part part) throws InterruptedException {
        log.info("Begin construction of car body");
        Thread.sleep(1000);
        log.info("Car body constructed");
        return new CarBody(part.getValue());
    }
}
