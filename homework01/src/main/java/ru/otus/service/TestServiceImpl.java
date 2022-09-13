package ru.otus.service;

import ru.otus.exception.MismatchInput;
import ru.otus.model.Question;
import java.util.Scanner;

public class TestServiceImpl implements TestService{

    private final QuestionService questionService;
    private final IOService ioServiceStreams;

    public TestServiceImpl(QuestionService questionService, IOServiceStreams ioServiceStreams) {
        this.questionService = questionService;
        this.ioServiceStreams = ioServiceStreams;
    }

    @Override
    public void test() {
        for (Question question: questionService.findAll()) {
            ioServiceStreams.outputString(String.format("#%d %s", question.getId(), question.getQuestionText()));
            question.getAnswers().forEach(
                    answer -> ioServiceStreams.outputString(String.format("  #%d %s", answer.getId(), answer.getAnswerText())));
            while (true) {
                try {
                    ioServiceStreams.readInt();
                    break;
                } catch (MismatchInput ex) {
                    ioServiceStreams.outputString("It is wrong input. Try again");

                }
            }
        }
    }
}
