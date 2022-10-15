package ru.otus.homework05.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutputServiceImpl implements OutputService {

    private OutputProvider outputProvider;

    @Autowired
    public OutputServiceImpl(OutputProvider outputProvider) {
        this.outputProvider = outputProvider;
    }

    @Override
    public void outputString(String message) {
        outputProvider.getOutput().
    }
}
