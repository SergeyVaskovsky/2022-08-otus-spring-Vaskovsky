package ru.otus.homework15.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework15.model.Engine;
import ru.otus.homework15.model.Part;

@Service
@RequiredArgsConstructor
public class EngineService {
    private final OutputService outputService;
    public Engine produce(Part part) throws InterruptedException {
        outputService.outputString("Begin construction of engine");
        Thread.sleep(1000);
        outputService.outputString("Engine constructed");
        return new Engine(Float.parseFloat(part.getValue()));
    }
}
