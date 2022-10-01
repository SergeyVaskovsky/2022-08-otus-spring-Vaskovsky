package ru.otus;

import ru.otus.service.IOProvider;

import java.io.InputStream;
import java.io.PrintStream;

public class IOProviderImplForTest implements IOProvider {

    private final PrintStream out;
    private final InputStream in;

    public IOProviderImplForTest(PrintStream out, InputStream in) {
        this.out = out;
        this.in = in;
    }

    @Override
        public InputStream getInput() {
            return in;
        }

        @Override
        public PrintStream getOutput() {
            return out;
        }

}
