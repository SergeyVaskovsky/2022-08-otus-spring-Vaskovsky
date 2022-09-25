package ru.otus.service;

import java.io.InputStream;
import java.io.PrintStream;

public interface IOProvider {
    InputStream getInput();

    PrintStream getOutput();
}
