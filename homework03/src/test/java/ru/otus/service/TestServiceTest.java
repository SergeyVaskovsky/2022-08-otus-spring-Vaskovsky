package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.config.ScoreProvider;
import ru.otus.model.Answer;
import ru.otus.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Class TestService")
@SpringBootTest(classes = TestServiceImpl.class)
public class TestServiceTest {
    @Autowired
    private TestServiceImpl testService;
    @MockBean
    private QuestionService questionService;
    @MockBean
    private IOService ioService;
    @MockBean
    private MessageSourceWrapper messageSourceWrapper;
    @MockBean
    private ScoreProvider scoreProvider;

    private static Stream<Arguments> provideParamsForTest() {
        return Stream.of(
                Arguments.of("success.message", 2),
                Arguments.of("fail.message", 1)
        );
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTest")
    @DisplayName("Test TestService")
    public void shouldDoTest(String result, int answer) {
        List<Question> questions = List.of(
                new Question(1, "How many planets are there in the solar system?",
                        List.of(new Answer(1, "7", false),
                                new Answer(2, "8", true),
                                new Answer(3, "9", false))),
                new Question(2, "How many oceans are there on the Earth?",
                        List.of(new Answer(1, "4", false),
                                new Answer(2, "5", true),
                                new Answer(3, "6", false))),
                new Question(3, "How many presidents were there in the USSR?",
                        List.of(new Answer(1, "0", false),
                                new Answer(2, "1", true),
                                new Answer(3, "2", false)))
        );
        when(questionService.findAll()).thenReturn(questions);
        when(scoreProvider.getScore()).thenReturn(2);
        List<String> expectedKeys = List.of("lastname", "firstname", result);
        List<String> keys = new ArrayList<>();

        when(ioService.readInt()).thenReturn(answer);

        when(messageSourceWrapper.getMessage(any())).thenAnswer(a -> {
            keys.add(a.getArgument(0));
            return a.getArgument(0);
        });

        testService.test();
        assertThat(keys).containsExactlyInAnyOrderElementsOf(expectedKeys);
        verify(ioService, times(1)).outputString(result);
    }
}
