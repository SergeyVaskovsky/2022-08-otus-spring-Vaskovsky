package ru.otus.homework11.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OutputServiceImpl implements OutputService {

    private final OutputProvider outputProvider;

    @Override
    public void outputString(String message) {
        outputProvider.getOutput().println(message);
    }
}
