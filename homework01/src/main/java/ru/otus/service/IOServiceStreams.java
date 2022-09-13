package ru.otus.service;

import ru.otus.exception.MismatchInput;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class IOServiceStreams implements IOService {

    private static final String WRONG_INPUT = "wrong";
    private final PrintStream output;
    private final Scanner input;

    public IOServiceStreams(PrintStream outputStream, InputStream inputStream) {
        output = outputStream;
        input = new Scanner(inputStream);
    }

    @Override
    public int readInt() {
        String inputString;
        inputString = getInput();
        if (WRONG_INPUT.equals(inputString)) {
            input.next();
            throw new MismatchInput("Input is not an integer");
        }
        return Integer.parseInt(inputString);
    }

    @Override
    public void outputString(String s) {
        output.println(s);
    }

    private String getInput() {
        return input.hasNextInt() ? input.next() : WRONG_INPUT;
    }
}
