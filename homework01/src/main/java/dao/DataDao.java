package dao;

import model.Question;

public interface DataDao {
    Question findByNum(int num);
    int questionCount();
}
