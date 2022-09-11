package ru.otus.dao;

import ru.otus.model.Question;

import java.util.List;

public interface QuestionDao {
    //Question findById(int id);
    //int questionsCount();
    List<Question> findAll();
}
