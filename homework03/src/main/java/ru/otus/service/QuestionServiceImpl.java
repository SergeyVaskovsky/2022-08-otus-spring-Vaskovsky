package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.dao.QuestionDao;
import ru.otus.model.Question;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao dao;

    public QuestionServiceImpl(QuestionDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Question> findAll() {
        return dao.findAll();
    }
}
