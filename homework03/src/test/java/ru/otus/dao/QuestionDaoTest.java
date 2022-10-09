package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.model.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Class QuestionCsvDao")
@SpringBootTest
public class QuestionDaoTest {

    @Autowired
    private QuestionDao questionDao;

    @DisplayName("Test findAll method")
    @Test
    public void shouldCorrectReturnAllQuestions() {
        List<Question> questions = questionDao.findAll();
        assertEquals(3, questions.size());
        assertEquals("How many planets are there in the solar system?", questions.get(0).getQuestionText());
    }
}
