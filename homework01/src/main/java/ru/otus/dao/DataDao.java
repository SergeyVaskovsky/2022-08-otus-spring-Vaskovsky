package ru.otus.dao;

import ru.otus.model.Question;

public interface DataDao {
    Question findById(int id);
    int questionsCount();
}
