package ru.otus.service;

import ru.otus.dao.DataDao;
import ru.otus.model.Question;

public class TestServiceImpl implements TestService {

    private final DataDao dao;

    public TestServiceImpl(DataDao dao) {
        this.dao = dao;
    }

    @Override
    public Question findById(int id){
        return dao.findById(id);
    }

    @Override
    public int questionsCount() {
        return dao.questionsCount();
    }

}
