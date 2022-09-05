package ru.otus;

import ru.otus.dao.DataDao;
import ru.otus.model.Question;

public class DataForTestDao implements DataDao {

    @Override
    public Question findById(int id) {
        switch (id) {
            case 1: return new Question(1, "Question 1");
            default: throw new RuntimeException(String.format("Question #%d not found", id));
        }
    }

    @Override
    public int questionsCount() {
        return 1;
    }


}
