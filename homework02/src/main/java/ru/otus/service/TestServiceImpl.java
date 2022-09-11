package ru.otus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.model.Answer;
import ru.otus.model.Question;

@Service
public class TestServiceImpl implements TestService{

    private final QuestionService questionService;

    private final IOServiceStreams ioServiceStreams;

    @Value("${score}")
    private int score;

    @Autowired
    public TestServiceImpl(QuestionService questionService, IOServiceStreams ioServiceStreams) {
        this.questionService = questionService;
        this.ioServiceStreams = ioServiceStreams;
    }

    @Override
    public void test() {
        getName();
        int testScore = 0;
        for (Question question : questionService.findAll()) {
            if (outputQuestion(question) == ioServiceStreams.readInt()) testScore++;
        }
        if (testScore >= score)
            ioServiceStreams.outputString("Test passed");
        else
            ioServiceStreams.outputString("Test failed");
    }

    private int outputQuestion(Question question) {
        int rightAnswerId = -1;
        ioServiceStreams.outputString(String.format("#%d %s", question.getId(), question.getQuestionText()));
        for (Answer answer : question.getAnswers()) {
            ioServiceStreams.outputString(String.format("  #%d %s", answer.getId(), answer.getAnswerText()));
            if (answer.isCorrect()) rightAnswerId = answer.getId();
        }
        return rightAnswerId;
    }

    private void getName() {
        ioServiceStreams.outputString("Enter your last name: ");
        ioServiceStreams.readString();
        ioServiceStreams.outputString("Enter your first name: ");
        ioServiceStreams.readString();
    }
}
