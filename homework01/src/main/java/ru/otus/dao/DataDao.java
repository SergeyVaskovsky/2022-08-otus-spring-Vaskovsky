package ru.otus.dao;

import ru.otus.model.Question;

public interface DataDao {
    Question findByNum(int num);
    int questionCount();
}
