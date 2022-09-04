package ru.otus.model;

public class Answer {
    private int id;
    private String answerText;
    private boolean correct;

    public Answer(int id, String text, boolean correct) {
        this.id = id;
        this.answerText = text;
        this.correct = correct;
    }

    public int getId() {
        return id;
    }

    public String getAnswerText() {
        return answerText;
    }

    public boolean isCorrect() {
        return correct;
    }
}
