package ru.otus;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.model.Question;
import ru.otus.service.TestService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");

        TestService service = context.getBean(TestService.class);

        Scanner scanner = new Scanner(System.in);

        for (int i = 1; i <= service.questionCount(); i++) {
            Question question = service.findByNum(i);
            System.out.printf("#%d %s%n", question.getId(), question.getQuestionText());
            question.getAnswers().forEach(
                    answer -> System.out.printf("  #%d %s%n", answer.getId(), answer.getAnswerText()));
            scanner.nextInt();
        }

        context.close();
    }
}
