package ru.otus.homework15.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework15.model.Options;
import ru.otus.homework15.model.Part;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class OptionsService {
    private final OutputService outputService;
    public Options produce(Part part) throws InterruptedException {
        outputService.outputString("Begin construction of options");
        Thread.sleep(3000);
        outputService.outputString("Options constructed");
        return new Options(Arrays.asList(part.getValue().split(", ")));
    }
}
