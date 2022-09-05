package ru.otus;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.model.Question;
import ru.otus.service.TestService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Class TestServiceTest")
public class TestServiceImplTest {

    private static TestService testService;

    @BeforeAll
    public static void setDao(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        testService = context.getBean(TestService.class);
    }

    @DisplayName("Finding test by id is correct")
    @Test
    public void shouldCorrectFindTestById() {
        Question question = testService.findById(1);
        assertEquals(1, question.getId());
        assertEquals("Question 1", question.getQuestionText());
    }

    @DisplayName("Finding test by id is incorrect")
    @Test
    public void shouldInCorrectFindTestById() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            Question question = testService.findById(2);
        });
        assertEquals("Question #2 not found", exception.getMessage());
    }
}


