package ru.otus.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ru.otus.model.Question;
import ru.otus.model.dto.TestDto;
import ru.otus.service.ConverterService;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
@PropertySource("classpath:application.properties")
public class QuestionCsvDao implements QuestionDao {
    private String testFileName;
    @Override
    public List<Question> findAll(){

        List<TestDto> beans =
                new CsvToBeanBuilder(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(testFileName)))
                .withType(TestDto.class).build().parse();

        var converterService = new ConverterService();

        List<Question> questions = new ArrayList<>();
        beans.forEach(bean -> questions.add(converterService.convert(bean)));

        return questions;
    }

    public QuestionCsvDao(@Value("${data.filename}") String testFileName) {
        this.testFileName = testFileName;
    }
}
