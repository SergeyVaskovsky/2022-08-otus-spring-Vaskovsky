package ru.otus.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Service
public class IOServiceStreams implements InputService, OutputService{

    private static final String WRONG_INPUT = "wrong";
    private final PrintStream output;
    private final Scanner input;

    public IOServiceStreams(@Value("${output}") PrintStream output, @Value("${input}") InputStream input) {
        this.output = output;
        this.input = new Scanner(input);
    }

    @Override
    public int readInt() {
        String result = getInput();
        while (WRONG_INPUT.equals(result)){
            outputString("It is wrong input. Try again.");
            input.next();
            result = getInput();
        }
        return Integer.parseInt(result);
    }

    @Override
    public String readString() {
        return input.next();
    }

    private String getInput() {
        return input.hasNextInt() ? input.next() : WRONG_INPUT;
    }

    @Override
    public void outputString(String s) {
        output.println(s);
    }
}
