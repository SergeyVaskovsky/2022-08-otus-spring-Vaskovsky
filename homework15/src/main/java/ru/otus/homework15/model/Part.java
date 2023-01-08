package ru.otus.homework15.model;

import lombok.Value;

@Value
public class Part {
    private static final long serialVersionUID = 1L;

    public enum Descriptor {
        CAR_BODY_NAME, ENGINE, OPTIONS
    }

    Descriptor descriptor;
    String value;

    public Part(Descriptor d, String value) {
        this.descriptor = d;
        this.value = value;
    }

}
