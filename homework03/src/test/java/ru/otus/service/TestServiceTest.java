package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.config.FileNameProvider;
import ru.otus.config.ScoreProvider;
import ru.otus.dao.QuestionCsvDao;
import ru.otus.dao.QuestionsConverter;
import ru.otus.model.Question;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {TestServiceImpl.class, QuestionCsvDao.class, QuestionsConverter.class})
public class TestServiceTest {
    @Autowired
    private TestServiceImpl testService;
    @Autowired
    private QuestionCsvDao questionDao;
    @Autowired
    private QuestionsConverter questionsConverter;
    @MockBean
    private QuestionService questionService;
    @MockBean
    private IOService ioService;
    @MockBean
    private MessageSourceWrapper messageSourceWrapper;
    @MockBean
    private ScoreProvider scoreProvider;
    @MockBean
    private FileNameProvider fileNameProvider;

    @DisplayName("Test TestService success")
    @Test
    public void shouldPassTest() {
        doTest(true);
    }

    @DisplayName("Test TestService fail")
    @Test
    public void shouldFailTest() {
        doTest(false);
    }

    private void doTest(boolean isSuccess) {
        when(fileNameProvider.getFileName()).thenReturn("data.csv");
        when(scoreProvider.getScore()).thenReturn(2);
        List<Question> questions = questionDao.findAll();
        when(questionService.findAll()).thenReturn(questions);

        List<String> expectedKeys = List.of("lastname", "firstname", isSuccess ? "success.message" : "fail.message");
        List<String> keys = new ArrayList<>();

        when(ioService.readInt()).thenReturn(isSuccess ? 2 : 1);

        when(messageSourceWrapper.getMessage(any())).thenAnswer(a -> {
            keys.add(a.getArgument(0));
            return a.getArgument(0);
        });

        testService.test();
        assertThat(keys).containsExactlyInAnyOrderElementsOf(expectedKeys);
        verify(ioService, times(1)).outputString(isSuccess ? "success.message" : "fail.message");
    }
}
