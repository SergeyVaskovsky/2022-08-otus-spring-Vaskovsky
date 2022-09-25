package ru.otus.testclasses;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import ru.otus.Homework03ApplicationTests;
import ru.otus.config.FileNameProvider;
import ru.otus.config.LocaleProvider;
import ru.otus.config.ScoreProvider;
import ru.otus.dao.QuestionCsvDao;
import ru.otus.dao.QuestionDao;
import ru.otus.dao.QuestionsConverter;
import ru.otus.service.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestServiceTest extends Homework03ApplicationTests {

    @Autowired
    MessageSource messageSource;
    @Autowired
    private FileNameProvider fileNameProvider;
    @Autowired
    private ScoreProvider scoreProvider;
    @Autowired
    private LocaleProvider localeProvider;

    @DisplayName("Test TestService fail")
    @Test
    public void shouldFailTest() throws IOException {
        TestService testService = getTestService("output_fail", "input_for_fail");
        testService.test();
        try (Stream<String> output = Files.lines(Paths.get("output_fail"), StandardCharsets.UTF_8)) {
            Assertions.assertTrue(output.collect(Collectors.joining(System.lineSeparator())).contains("Test failed"));
        }

    }

    @DisplayName("Test TestService success")
    @Test
    public void shouldPassTest() throws IOException {
        TestService testService = getTestService("output_success", "input_for_success");
        testService.test();
        try (Stream<String> output = Files.lines(Paths.get("output_success"), StandardCharsets.UTF_8)) {
            Assertions.assertTrue(output.collect(Collectors.joining(System.lineSeparator())).contains("Test passed"));
        }
    }

    private TestService getTestService(String outputName, String inputName) throws FileNotFoundException {
        QuestionsConverter questionsConverter = new QuestionsConverter();
        QuestionDao questionDao = new QuestionCsvDao(questionsConverter, fileNameProvider);
        QuestionService questionService = new QuestionServiceImpl(questionDao);
        IOProvider ioProvider = new IOProviderImplForTest(new PrintStream(outputName), new FileInputStream(inputName));
        IOService ioService = new IOServiceStreams(ioProvider);
        MessageSourceWrapper messageSourceWrapper = new MessageSourceWrapper(localeProvider, messageSource);
        return new TestServiceImpl(questionService, ioService, scoreProvider, messageSourceWrapper);
    }


    static class IOProviderImplForTest implements IOProvider {

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

}
