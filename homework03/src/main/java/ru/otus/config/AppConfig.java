package ru.otus.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;

@ConfigurationProperties(prefix = "prop")
@Component
public class AppConfig implements
        FileNameProvider,
        LocaleProvider,
        ScoreProvider {

    private String fileNamePattern;
    private int score;
    private Locale locale;

    @Override
    public String getFileName() {
        return String.format(fileNamePattern, locale.toString());
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setFileNamePattern(String fileNamePattern) {
        this.fileNamePattern = fileNamePattern;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int getScore() {
        return score;
    }
}
