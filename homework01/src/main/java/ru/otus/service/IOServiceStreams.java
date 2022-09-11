package ru.otus.service;

import ru.otus.exception.MismatchInput;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class IOServiceStreams implements InputService, OutputService{

    private static final String WRONG_INPUT = "wrong";
    private final PrintStream output;
    private final Scanner input;

    public IOServiceStreams(PrintStream outputStream, InputStream inputStream) {
        output = outputStream;
        input = new Scanner(inputStream);
    }

    @Override
    public int readInt() {
        String result = getInput();
        while (WRONG_INPUT.equals(result)){
            outputString("It is wrong input. Try again");
            input.next();
            result = getInput();
        }
        return Integer.parseInt(result);
    }

    private String getInput() {
        return input.hasNextInt() ? input.next() : WRONG_INPUT;
    }

    @Override
    public void outputString(String s) {
        output.println(s);
    }
}
