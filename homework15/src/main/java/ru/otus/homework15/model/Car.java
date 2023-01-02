package ru.otus.homework15.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Car {
    //String modelName;
    CarBody carBody;
    Engine engine;
    Options options;
}
