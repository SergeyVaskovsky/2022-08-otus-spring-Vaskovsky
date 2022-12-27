package ru.otus.homework15.service;

import org.springframework.stereotype.Service;
import ru.otus.homework15.model.Butterfly;
import ru.otus.homework15.model.Cocoon;

@Service
public class MagicalTurningInto {
    public Butterfly turnInto(Cocoon cocoon) {
        return new Butterfly(cocoon.getName());
    }
}
