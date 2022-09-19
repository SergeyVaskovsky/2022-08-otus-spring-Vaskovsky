package ru.otus;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Locale;

@ConfigurationProperties(prefix = "application-test")
@Configuration
public class AppConfig {

    private final static InputStream in = System.in;
    private final static PrintStream out = System.out;
    private String fileName;
    private int score;
    private InputStream input = in;
    private PrintStream output = out;
    private Locale locale;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getFileName() {
        return fileName;
    }

    public int getScore() {
        return score;
    }

    public InputStream getInput() {
        return input;
    }

    public PrintStream getOutput() {
        return output;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setInput(InputStream input) {
        this.input = input;
    }

    public void setOutput(PrintStream output) {
        this.output = output;
    }
}
