package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.service.TestService;

@SpringBootApplication
public class Homework03Application {

    public static void main(String[] args) {
        TestService service = SpringApplication
                .run(Homework03Application.class, args)
                .getBean(TestService.class);
        service.test();
    }

}
