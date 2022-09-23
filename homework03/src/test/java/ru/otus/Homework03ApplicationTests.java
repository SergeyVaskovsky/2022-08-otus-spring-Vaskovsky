package ru.otus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.config.AppConfig;
import ru.otus.dao.QuestionCsvDao;
import ru.otus.dao.QuestionDao;
import ru.otus.dao.QuestionsConverter;
import ru.otus.model.Question;
import ru.otus.service.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = TestAppConfig.class)
@ImportAutoConfiguration(MessageSourceAutoConfiguration.class)
@ActiveProfiles("test")
class Homework03ApplicationTests {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private TestAppConfig testAppConfig;

	@DisplayName("Test findAll method")
	@Test
	public void shouldCorrectReturnAllQuestions() {
		QuestionsConverter questionsConverter = new QuestionsConverter();
		QuestionDao questionDao = new QuestionCsvDao(questionsConverter, testAppConfig.getFileNamePattern());
		List<Question> questions = questionDao.findAll();
		assertEquals(3, questions.size());
		assertEquals("How many planets are there in the solar system?", questions.get(0).getQuestionText());
	}

	@DisplayName("Test TestService fail")
	@Test
	public void shouldFailTest() throws IOException {
		QuestionsConverter questionsConverter = new QuestionsConverter();
		QuestionDao questionDao = new QuestionCsvDao(questionsConverter, testAppConfig.getFileNamePattern());
		QuestionService questionService = new QuestionServiceImpl(questionDao);
		IOService ioService = new IOServiceStreams(new PrintStream("output_fail"), new FileInputStream("input_for_fail"));
		AppConfig appConfig = new AppConfig();
		appConfig.setLocale(testAppConfig.getLocale());
		appConfig.setScore(testAppConfig.getScore());
		TestService testService = new TestServiceImpl(questionService, ioService, appConfig, messageSource);
		testService.test();
		try (Stream<String> output = Files.lines(Paths.get("output_fail"), StandardCharsets.UTF_8)) {
			assertTrue(output.collect(Collectors.joining(System.lineSeparator())).contains("Test failed"));
		}

	}

	@DisplayName("Test TestService success")
	@Test
	public void shouldPassTest() throws IOException {
		QuestionsConverter questionsConverter = new QuestionsConverter();
		QuestionDao questionDao = new QuestionCsvDao(questionsConverter, testAppConfig.getFileNamePattern());
		QuestionService questionService = new QuestionServiceImpl(questionDao);
		IOService ioService = new IOServiceStreams(new PrintStream("output_success"), new FileInputStream("input_for_success"));
		AppConfig appConfig = new AppConfig();
		appConfig.setLocale(testAppConfig.getLocale());
		appConfig.setScore(testAppConfig.getScore());
		TestService testService = new TestServiceImpl(questionService, ioService, appConfig, messageSource);
		testService.test();
		try (Stream<String> output = Files.lines(Paths.get("output_success"), StandardCharsets.UTF_8)) {
			assertTrue(output.collect(Collectors.joining(System.lineSeparator())).contains("Test passed"));
		}
	}
}
