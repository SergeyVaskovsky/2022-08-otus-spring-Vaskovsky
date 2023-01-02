package ru.otus.homework15.service;

import org.springframework.stereotype.Component;

import java.io.PrintStream;

@Component
public class OutputProviderImpl implements OutputProvider {

    @Override
    public PrintStream getOutput() {
        return System.out;
    }
}