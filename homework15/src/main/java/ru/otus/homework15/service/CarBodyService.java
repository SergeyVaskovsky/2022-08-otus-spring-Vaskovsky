package ru.otus.homework15.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework15.model.CarBody;
import ru.otus.homework15.model.Part;

@Service
@RequiredArgsConstructor
public class CarBodyService {
    private final OutputService outputService;

    public CarBody produce(Part part) throws InterruptedException {
        outputService.outputString("Begin construction of car body");
        Thread.sleep(1000);
        outputService.outputString("Car body constructed");
        return new CarBody(part.getValue());
    }
}
