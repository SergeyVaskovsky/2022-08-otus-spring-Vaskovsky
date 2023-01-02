package ru.otus.homework15.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.homework15.model.Engine;
import ru.otus.homework15.model.Part;

@Service
@Slf4j
public class EngineService {
    public Engine produce(Part part) throws InterruptedException {
        log.info("Begin construction of engine");
        Thread.sleep(1000);
        log.info("Engine constructed");
        return new Engine(Float.parseFloat(part.getValue()));
    }
}
