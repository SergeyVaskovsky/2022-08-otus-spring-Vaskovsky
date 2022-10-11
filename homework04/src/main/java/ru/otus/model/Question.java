package ru.otus.model;

import java.util.List;

public class Question {
    private final int id;
    private final String questionText;
    private List<Answer> answers;

    public Question(int id, String text, List<Answer> answers) {
        this.id = id;
        this.questionText = text;
        this.answers = answers;
    }

    public int getId() {
        return id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
