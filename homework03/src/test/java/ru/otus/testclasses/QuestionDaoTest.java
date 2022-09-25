package ru.otus.testclasses;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.Homework03ApplicationTests;
import ru.otus.config.FileNameProvider;
import ru.otus.dao.QuestionCsvDao;
import ru.otus.dao.QuestionDao;
import ru.otus.dao.QuestionsConverter;
import ru.otus.model.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Class QuestionCsvDao")
public class QuestionDaoTest extends Homework03ApplicationTests {

    @Autowired
    private FileNameProvider fileNameProvider;

    @DisplayName("Test findAll method")
    @Test
    public void shouldCorrectReturnAllQuestions() {
        QuestionsConverter questionsConverter = new QuestionsConverter();
        QuestionDao questionDao = new QuestionCsvDao(questionsConverter, fileNameProvider);
        List<Question> questions = questionDao.findAll();
        assertEquals(3, questions.size());
        assertEquals("How many planets are there in the solar system?", questions.get(0).getQuestionText());
    }
}
