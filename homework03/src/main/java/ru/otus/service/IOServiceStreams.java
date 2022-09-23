package ru.otus.service;

import ru.otus.exception.MismatchInputException;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class IOServiceStreams implements IOService {

    private static final String WRONG_INPUT = "wrong";

    private final PrintStream output;

    private final Scanner input;

    public IOServiceStreams(PrintStream out, InputStream in) {
        this.output = out;
        this.input = new Scanner(in);
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
