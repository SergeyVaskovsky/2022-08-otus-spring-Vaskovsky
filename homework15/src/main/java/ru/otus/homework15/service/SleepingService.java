package ru.otus.homework15.service;

import org.springframework.stereotype.Service;
import ru.otus.homework15.model.Caterpillar;
import ru.otus.homework15.model.Cocoon;

@Service
public class SleepingService {
    public Cocoon sleep(Caterpillar caterpillar) {
        return new Cocoon(caterpillar.getName());
    }
}
