package ru.otus.homework15.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintStream;

@Service
public class OutputServiceImpl implements OutputService {
    private final PrintStream output;

    @Autowired
    public OutputServiceImpl(OutputProvider outputProvider) {
        this.output = outputProvider.getOutput();
    }

    @Override
    public void outputString(String s) {
        output.println(s);
    }
}
