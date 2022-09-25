package ru.otus.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.config.FileNameProvider;
import ru.otus.exception.IOOrCloseException;
import ru.otus.exception.ResourceIsNullException;
import ru.otus.model.Question;
import ru.otus.model.dto.TestDto;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionCsvDao implements QuestionDao {
    private final String testFileName;

    private final QuestionsConverter questionsConverter;

    @Autowired
    public QuestionCsvDao(
            QuestionsConverter questionsConverter,
            FileNameProvider fileNameProvider) {
        this.testFileName = fileNameProvider.getFileName();
        this.questionsConverter = questionsConverter;
    }

    @Override
    public List<Question> findAll() {
        if (testFileName == null) {
            throw new ResourceIsNullException("The filename of the file with data for the test is null");
        }
        List<TestDto> beans;

        try (InputStream resource = this.getClass().getClassLoader().getResourceAsStream(testFileName)) {
            if (resource == null) {
                throw new ResourceIsNullException("Cannot get resource from the file with the filename " + testFileName);
            }
            beans =
                    new CsvToBeanBuilder<TestDto>(new InputStreamReader(resource))
                            .withType(TestDto.class)
                            .build()
                            .parse();
        } catch (IOException e) {
            throw new IOOrCloseException("Exception on parse or close resource");
        }

        List<Question> questions = new ArrayList<>();
        if (beans != null) {
            beans.forEach(bean -> questions.add(questionsConverter.convert(bean)));
        }

        return questions;
    }
}
