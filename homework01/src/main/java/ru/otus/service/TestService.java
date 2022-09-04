package ru.otus.service;

import ru.otus.model.Question;

public interface TestService {
    Question findByNum(int num);
    int questionCount();
}
