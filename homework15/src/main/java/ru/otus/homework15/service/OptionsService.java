package ru.otus.homework15.service;

import org.springframework.stereotype.Service;
import ru.otus.homework15.model.Options;
import ru.otus.homework15.model.Order;
import ru.otus.homework15.model.Part;

import java.util.Arrays;

@Service
public class OptionsService {
    public Options produce(Part part) throws InterruptedException {
        Thread.sleep(3000);
        System.out.println("Options constructed");
        return new Options(Arrays.asList(part.getValue().split(", ")));
    }
}
