package ru.otus.homework05.service;

import org.springframework.stereotype.Service;

import java.io.PrintStream;

@Service
public class OutputProviderImpl implements OutputProvider {

    @Override
    public PrintStream getOutput() {
        return System.out;
    }
}
