package ru.otus.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.otus.dao.QuestionCsvDao;
import ru.otus.dao.QuestionDao;
import ru.otus.dao.QuestionsConverter;
import ru.otus.service.IOService;
import ru.otus.service.IOServiceStreams;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Locale;


@ConfigurationProperties(prefix = "app")
@Component
public class AppConfig {

    private final static InputStream in = System.in;
    private final static PrintStream out = System.out;
    private String fileNamePattern;
    private int score;
    private InputStream input = in;
    private PrintStream output = out;
    private Locale locale;

    @Bean
    public QuestionDao questionCsvDao(QuestionsConverter questionsConverter) {
        return new QuestionCsvDao(questionsConverter, String.format(fileNamePattern, locale.toString()));
    }

    @Bean
    public IOService ioService() {
        return new IOServiceStreams(out, in);
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getFileNamePattern() {
        return fileNamePattern;
    }

    public void setFileNamePattern(String fileNamePattern) {
        this.fileNamePattern = fileNamePattern;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public InputStream getInput() {
        return input;
    }

    public void setInput(InputStream input) {
        this.input = input;
    }

    public PrintStream getOutput() {
        return output;
    }

    public void setOutput(PrintStream output) {
        this.output = output;
    }
}
