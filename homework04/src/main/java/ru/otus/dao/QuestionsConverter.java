package ru.otus.dao;

import org.springframework.stereotype.Component;
import ru.otus.model.Answer;
import ru.otus.model.Question;
import ru.otus.model.dto.TestDto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionsConverter {
    public Question convert(TestDto dto) {
        Question question = new Question(Integer.parseInt(dto.getNumber()), dto.getText(), new ArrayList<>());
        for (String answerIdDto : dto.getAnswer().keySet()) {
            List<String> answerDto = new ArrayList<>(dto
                    .getAnswer()
                    .get(answerIdDto));
            Answer answer = new Answer(
                    Integer.parseInt(answerDto.get(0)),
                    answerDto.get(1),
                    "t".equals(answerDto.get(2)));
            question.getAnswers().add(answer);
        }
        question.setAnswers(
                question
                        .getAnswers()
                        .stream()
                        .sorted(Comparator.comparing(Answer::getId))
                        .collect(Collectors.toList()));
        return question;
    }
}
