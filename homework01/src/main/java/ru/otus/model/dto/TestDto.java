package ru.otus.model.dto;

import com.opencsv.bean.CsvBindAndJoinByName;
import com.opencsv.bean.CsvBindByName;
import org.apache.commons.collections4.MultiValuedMap;

public class TestDto {
    @CsvBindByName(column = "question_number")
    private String number;

    @CsvBindByName(column = "question_text")
    private String text;

    @CsvBindAndJoinByName(column = "answer [0-9]+", elementType = String.class)
    private MultiValuedMap<String, String> answer;

    public String getNumber() {
        return number;
    }

    public String getText() {
        return text;
    }

    public MultiValuedMap<String, String> getAnswer() {
        return answer;
    }
}
