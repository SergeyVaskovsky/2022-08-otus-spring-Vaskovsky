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
    private final IOService ioServiceStreams;
    private final AppConfig appConfig;

    private final MessageSource messageSource;

    @Autowired
    public TestServiceImpl(QuestionService questionService, IOService ioServiceStreams, AppConfig appConfig, MessageSource messageSource) {
        this.questionService = questionService;
        this.ioServiceStreams = ioServiceStreams;
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
                    if (outputQuestion(question) == ioServiceStreams.readInt()) testScore++;
                    break;
                } catch (MismatchInputException ex) {
                    ioServiceStreams.outputString(messageSource.getMessage("not.number", null, appConfig.getLocale()));

                }
            }
        }

        if (testScore >= score)
            ioServiceStreams.outputString(messageSource.getMessage("success.message", null, appConfig.getLocale()));
        else
            ioServiceStreams.outputString(messageSource.getMessage("fail.message", null, appConfig.getLocale()));
    }

    private int outputQuestion(Question question) {
        int rightAnswerId = -1;
        String questionText = messageSource.getMessage("question" + question.getId(), null, appConfig.getLocale());
        ioServiceStreams.outputString(String.format("#%d %s", question.getId(), questionText));
        for (Answer answer : question.getAnswers()) {
            if (question.getId() == 4) {
                ioServiceStreams.outputString(String.format("  #%d %s", answer.getId(),
                        messageSource.getMessage("answer" + answer.getId(), null, appConfig.getLocale())));
            } else {
                ioServiceStreams.outputString(String.format("  #%d %s", answer.getId(), answer.getAnswerText()));
            }
            if (answer.isCorrect()) rightAnswerId = answer.getId();
        }
        return rightAnswerId;
    }

    private void getName() {
        ioServiceStreams.outputString(messageSource.getMessage("lastname", null, appConfig.getLocale()));
        ioServiceStreams.readString();
        ioServiceStreams.outputString(messageSource.getMessage("firstname", null, appConfig.getLocale()));
        ioServiceStreams.readString();
    }
}
