package ru.otus.service;

import ru.otus.model.Question;

public interface TestService {
    Question findById(int id);
    int questionsCount();
}
