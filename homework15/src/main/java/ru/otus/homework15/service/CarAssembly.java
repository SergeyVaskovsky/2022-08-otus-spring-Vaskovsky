package ru.otus.homework15.service;

import org.springframework.stereotype.Service;
import ru.otus.homework15.model.Car;
import ru.otus.homework15.model.CarBody;
import ru.otus.homework15.model.Engine;
import ru.otus.homework15.model.Options;

@Service
public class CarAssembly {
    public Car assemble(CarBody carBody, Engine engine, Options options) {
        return new Car(carBody, engine, options);
    }
}
