package ru.otus.dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import ru.otus.model.Answer;
import ru.otus.model.Question;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataCsvDao implements DataDao{

    private List<Question> questions = new ArrayList<>();
    
    @Override
    public Question findByNum(int id) {
        return questions
                .stream()
                .filter(question -> question.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("Question #%d not found", id)));
    }

    @Override
    public int questionCount() {
        return questions.size();
    }

    public DataCsvDao(String dataFileName) {

        try (CSVReader reader = new CSVReader(
                new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(dataFileName))
        )) {
            List<String[]> lineInArray = reader.readAll();
            Question question = null;
            for (String[] strings : lineInArray ) {
                //Если вопрос то:
                if (strings.length == 3 && "q".equals(strings[0])) {
                    question = new Question(
                            Integer.parseInt(strings[1]),
                            strings[2]);
                    questions.add(question);
                //иначе, если ответ то:
                } else if (
                        strings.length == 5 &&
                        "a".equals(strings[0]) &&
                        question.getId() == Integer.parseInt(strings[1])) {
                    Answer answer = new Answer(
                            Integer.parseInt(strings[2]),
                            strings[3],
                            "t".equals(strings[4]));
                    question.getAnswers().add(answer);
                } else {
                    throw new RuntimeException(String.format("File %s has wrong format", dataFileName));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }
}
