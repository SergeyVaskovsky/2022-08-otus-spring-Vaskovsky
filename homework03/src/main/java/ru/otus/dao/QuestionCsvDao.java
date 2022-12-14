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

    private final FileNameProvider fileNameProvider;
    private final QuestionsConverter questionsConverter;

    @Autowired
    public QuestionCsvDao(
            FileNameProvider fileNameProvider,
            QuestionsConverter questionsConverter
    ) {
        this.fileNameProvider = fileNameProvider;
        this.questionsConverter = questionsConverter;
    }

    @Override
    public List<Question> findAll() {
        if (fileNameProvider.getFileName() == null) {
            throw new ResourceIsNullException("The filename of the file with data for the test is null");
        }
        List<TestDto> beans;

        try (InputStream resource = this.getClass().getClassLoader().getResourceAsStream(fileNameProvider.getFileName())) {
            if (resource == null) {
                throw new ResourceIsNullException("Cannot get resource from the file with the filename " + fileNameProvider.getFileName());
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
