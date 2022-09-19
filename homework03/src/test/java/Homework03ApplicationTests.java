import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
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

@SpringBootTest
@ContextConfiguration(classes = AppConfig.class)
@ImportAutoConfiguration(MessageSourceAutoConfiguration.class)
class Homework03ApplicationTests {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private AppConfig appConfig;

	@DisplayName("Test findAll method")
	@Test
	public void shouldCorrectReturnAllQuestions() {
		QuestionsConverter questionsConverter = new QuestionsConverter();
		QuestionDao questionDao = new QuestionCsvDao(questionsConverter, appConfig);
		List<Question> questions = questionDao.findAll();
		assertEquals(3, questions.size());
		assertEquals("question1", questions.get(0).getQuestionText());
	}

	@DisplayName("Test TestService fail")
	@Test
	public void shouldFailTest() throws IOException {
		QuestionsConverter questionsConverter = new QuestionsConverter();
		QuestionDao questionDao = new QuestionCsvDao(questionsConverter, appConfig);
		QuestionService questionService = new QuestionServiceImpl(questionDao);
		appConfig.setOutput(new PrintStream("output_fail"));
		appConfig.setInput(new FileInputStream("input_for_fail"));
		IOService ioService = new IOServiceStreams(appConfig);
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
		QuestionDao questionDao = new QuestionCsvDao(questionsConverter, appConfig);
		QuestionService questionService = new QuestionServiceImpl(questionDao);
		appConfig.setOutput(new PrintStream("output_success"));
		appConfig.setInput(new FileInputStream("input_for_success"));
		IOService ioService = new IOServiceStreams(appConfig);
		TestService testService = new TestServiceImpl(questionService, ioService, appConfig, messageSource);
		testService.test();
		try (Stream<String> output = Files.lines(Paths.get("output_success"), StandardCharsets.UTF_8)) {
			assertTrue(output.collect(Collectors.joining(System.lineSeparator())).contains("Test passed"));
		}
	}
}
