package ru.otus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.config.ScoreProvider;
import ru.otus.exception.MismatchInputException;
import ru.otus.exception.TooManyMismatchInputsException;
import ru.otus.model.Answer;
import ru.otus.model.Question;

@Service
public class TestServiceImpl implements TestService {

    private static final byte LIMIT_ATTEMPTS = 10;
    private final QuestionService questionService;
    private final IOService ioService;
    private final MessageSourceWrapper messageSourceWrapper;
    private final ScoreProvider scoreProvider;

    @Autowired
    public TestServiceImpl(
            QuestionService questionService,
            IOService ioService,
            ScoreProvider scoreProvider,
            MessageSourceWrapper messageSourceWrapper) {
        this.questionService = questionService;
        this.ioService = ioService;
        this.messageSourceWrapper = messageSourceWrapper;
        this.scoreProvider = scoreProvider;
    }

    @Override
    public void test() {
        String studentName = getName();
        int testScore = 0;
        for (Question question : questionService.findAll()) {
            byte attemptsCount = 0;
            while (true) {
                try {
                    if (outputQuestionAndGetRightAnswer(question) == ioService.readInt()) {
                        testScore++;
                    }
                    break;
                } catch (MismatchInputException ex) {
                    ioService.outputString(messageSourceWrapper.getMessage("not.number"));
                    attemptsCount++;
                    if (attemptsCount > LIMIT_ATTEMPTS) {
                        throw new TooManyMismatchInputsException("Too many mismatch inputs");
                    }
                }
            }
        }

        if (testScore >= scoreProvider.getScore())
            ioService.outputString(messageSourceWrapper.getMessage("success.message"));
        else
            ioService.outputString(messageSourceWrapper.getMessage("fail.message"));
    }

    private int outputQuestionAndGetRightAnswer(Question question) {
        int rightAnswerId = -1;
        ioService.outputString(String.format("#%d %s", question.getId(), question.getQuestionText()));
        for (Answer answer : question.getAnswers()) {
            ioService.outputString(String.format("  #%d %s", answer.getId(), answer.getAnswerText()));
            if (answer.isCorrect()) {
                rightAnswerId = answer.getId();
            }
        }
        return rightAnswerId;
    }

    private String getName() {
        ioService.outputString(messageSourceWrapper.getMessage("lastname"));
        String lastname = ioService.readString();
        ioService.outputString(messageSourceWrapper.getMessage("firstname"));
        String firstname = ioService.readString();
        return String.format("%s %s", lastname, firstname);
    }
}
