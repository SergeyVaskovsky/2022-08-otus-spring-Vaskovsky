package ru.otus.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import ru.otus.exception.IOExceptionOnClose;
import ru.otus.exception.ResourceIsNull;
import ru.otus.model.Question;
import ru.otus.model.dto.TestDto;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class QuestionCsvDao implements QuestionDao {
    private final String testFileName;

    private final QuestionsConverter questionsConverter;

    public QuestionCsvDao(String testFileName, QuestionsConverter questionsConverter) {
        this.testFileName = testFileName;
        this.questionsConverter = questionsConverter;
    }
    @Override
    public List<Question> findAll(){
        if (testFileName == null) {
            throw new ResourceIsNull("The filename of the file with data for test is null");
        }
        List<TestDto> beans;

        try (InputStream resource = this.getClass().getClassLoader().getResourceAsStream(testFileName)) {
                if (resource == null) {
                    throw new ResourceIsNull("Cannot get resource from the file with the filename " + testFileName);
                }
                beans =
                    new CsvToBeanBuilder(new InputStreamReader(resource))
                            .withType(TestDto.class)
                            .build()
                            .parse();
        } catch (IOException e) {
            throw new IOExceptionOnClose("Exception on close resource");
        }

        List<Question> questions = new ArrayList<>();
        if (beans != null) {
            beans.forEach(bean -> questions.add(questionsConverter.convert(bean)));
        }

        return questions;
    }
}
