package ru.otus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.config.ScoreProvider;
import ru.otus.exception.MismatchInputException;
import ru.otus.model.Answer;
import ru.otus.model.Question;

@Service
public class TestServiceImpl implements TestService {

    private final int score;
    private final QuestionService questionService;
    private final IOService ioService;
    private final MessageSourceWrapper messageSourceWrapper;

    @Autowired
    public TestServiceImpl(
            QuestionService questionService,
            IOService ioService,
            ScoreProvider scoreProvider,
            MessageSourceWrapper messageSourceWrapper) {
        this.questionService = questionService;
        this.ioService = ioService;
        this.messageSourceWrapper = messageSourceWrapper;
        this.score = scoreProvider.getScore();
    }

    @Override
    public void test() {
        String studentName = getName();
        int testScore = 0;
        for (Question question : questionService.findAll()) {
            while (true) {
                try {
                    if (outputQuestion(question) == ioService.readInt()) testScore++;
                    break;
                } catch (MismatchInputException ex) {
                    ioService.outputString(messageSourceWrapper.getMessage("not.number", null));

                }
            }
        }

        if (testScore >= score)
            ioService.outputString(messageSourceWrapper.getMessage("success.message", null));
        else
            ioService.outputString(messageSourceWrapper.getMessage("fail.message", null));
    }

    private int outputQuestion(Question question) {
        int rightAnswerId = -1;
        ioService.outputString(String.format("#%d %s", question.getId(), question.getQuestionText()));
        for (Answer answer : question.getAnswers()) {
            ioService.outputString(String.format("  #%d %s", answer.getId(), answer.getAnswerText()));
            if (answer.isCorrect()) rightAnswerId = answer.getId();
        }
        return rightAnswerId;
    }

    private String getName() {
        ioService.outputString(messageSourceWrapper.getMessage("lastname", null));
        String lastname = ioService.readString();
        ioService.outputString(messageSourceWrapper.getMessage("firstname", null));
        String firstname = ioService.readString();
        return String.format("%s %s", lastname, firstname);
    }
}
