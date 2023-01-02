package ru.otus.homework15.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.homework15.model.Options;
import ru.otus.homework15.model.Part;

import java.util.Arrays;

@Service
@Slf4j
public class OptionsService {
    public Options produce(Part part) throws InterruptedException {
        log.info("Begin construction or options");
        Thread.sleep(3000);
        log.info("Options constructed");
        return new Options(Arrays.asList(part.getValue().split(", ")));
    }
}
