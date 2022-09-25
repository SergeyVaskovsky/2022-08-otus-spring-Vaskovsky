package ru.otus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.exception.MismatchInputException;

import java.io.PrintStream;
import java.util.Scanner;

@Service
public class IOServiceStreams implements IOService {

    private static final String WRONG_INPUT = "wrong";

    private final PrintStream output;

    private final Scanner input;

    @Autowired
    public IOServiceStreams(IOProvider ioProvider) {
        this.output = ioProvider.getOutput();
        this.input = new Scanner(ioProvider.getInput());
    }

    @Override
    public int readInt() {
        String inputString;
        inputString = getInput();
        if (WRONG_INPUT.equals(inputString)) {
            input.next();
            throw new MismatchInputException("Input is not an integer");
        }
        return Integer.parseInt(inputString);
    }

    @Override
    public String readString() {
        return input.next();
    }

    @Override
    public void outputString(String s) {
        output.println(s);
    }

    private String getInput() {
        return input.hasNextInt() ? input.next() : WRONG_INPUT;
    }
}
