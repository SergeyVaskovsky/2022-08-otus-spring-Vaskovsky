package ru.otus.service;

import ru.otus.exception.MismatchInput;
import ru.otus.model.Question;
import java.util.Scanner;

public class TestServiceImpl implements TestService{

    private final QuestionService questionService;
    private final IOServiceStreams ioServiceStreams;

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
            ioServiceStreams.readInt();
        }
    }
}
