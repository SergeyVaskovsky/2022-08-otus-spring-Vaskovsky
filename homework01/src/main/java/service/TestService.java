package service;

import model.Question;

public interface TestService {
    Question findByNum(int num);
    int questionCount();
}
