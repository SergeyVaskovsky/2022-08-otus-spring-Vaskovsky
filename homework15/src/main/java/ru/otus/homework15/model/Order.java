package ru.otus.homework15.model;

import lombok.Value;

import java.util.List;

@Value
public class Order {
    String bodyName;
    float engine;
    List<String> options;
}
