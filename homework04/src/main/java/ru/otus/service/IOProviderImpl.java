package ru.otus.service;

import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.PrintStream;

@Component
public class IOProviderImpl implements IOProvider {
    @Override
    public InputStream getInput() {
        return System.in;
    }

    @Override
    public PrintStream getOutput() {
        return System.out;
    }
}
