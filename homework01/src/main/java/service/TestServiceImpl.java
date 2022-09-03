package service;

import dao.DataDao;
import model.Question;

public class TestServiceImpl implements TestService {

    private final DataDao dao;

    public TestServiceImpl(DataDao dao) {
        this.dao = dao;
    }

    @Override
    public Question findByNum(int num){
        return dao.findByNum(num);
    }

    @Override
    public int questionCount() {
        return dao.questionCount();
    }

}
