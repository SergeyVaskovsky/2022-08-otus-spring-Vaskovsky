package ru.otus;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.model.Question;
import ru.otus.service.QuestionService;
import ru.otus.service.TestService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        TestService service = context.getBean(TestService.class);
        service.test();
        context.close();
    }
}
