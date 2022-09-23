package ru.otus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.config.AppConfig;
import ru.otus.exception.MismatchInputException;
import ru.otus.model.Answer;
import ru.otus.model.Question;

@Service
public class TestServiceImpl implements TestService {

    private final int score;
    private final QuestionService questionService;
    private final IOService ioService;
    private final AppConfig appConfig;

    private final MessageSource messageSource;

    @Autowired
    public TestServiceImpl(QuestionService questionService, IOService ioService, AppConfig appConfig, MessageSource messageSource) {
        this.questionService = questionService;
        this.ioService = ioService;
        this.appConfig = appConfig;
        score = appConfig.getScore();
        this.messageSource = messageSource;
    }

    @Override
    public void test() {
        getName();
        int testScore = 0;
        for (Question question : questionService.findAll()) {
            while (true) {
                try {
                    if (outputQuestion(question) == ioService.readInt()) testScore++;
                    break;
                } catch (MismatchInputException ex) {
                    ioService.outputString(messageSource.getMessage("not.number", null, appConfig.getLocale()));

                }
            }
        }

        if (testScore >= score)
            ioService.outputString(messageSource.getMessage("success.message", null, appConfig.getLocale()));
        else
            ioService.outputString(messageSource.getMessage("fail.message", null, appConfig.getLocale()));
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

    private void getName() {
        ioService.outputString(messageSource.getMessage("lastname", null, appConfig.getLocale()));
        ioService.readString();
        ioService.outputString(messageSource.getMessage("firstname", null, appConfig.getLocale()));
        ioService.readString();
    }
}
