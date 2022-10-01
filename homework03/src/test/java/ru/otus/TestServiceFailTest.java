package ru.otus;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ru.otus.service.IOProvider;
import ru.otus.service.TestService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
public class TestServiceFailTest {

    @Autowired
    private TestService testService;

    @DisplayName("Test TestService success")
    @Test
    public void shouldPassTest() throws IOException {
        testService.test();
        try (Stream<String> output = Files.lines(Paths.get("output_fail"), StandardCharsets.UTF_8)) {
            Assertions.assertTrue(output.collect(Collectors.joining(System.lineSeparator())).contains("Test failed"));
        }
    }

    @TestConfiguration
    static class NestedTestConfiguration {
        @Bean
        IOProvider ioProvider() throws FileNotFoundException {
            return new IOProviderImplForTest(new PrintStream("output_fail"), new FileInputStream("input_for_fail"));
        }
    }
}
