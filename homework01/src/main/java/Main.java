import model.Question;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.TestService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        TestService service = context.getBean(TestService.class);

        Scanner sc = new Scanner(System.in);

        for (int i = 1; i <= service.questionCount(); i++) {
            Question question = service.findByNum(i);
            System.out.printf("#%d %s%n", question.getId(), question.getQuestionText());
            question.getAnswers().forEach(
                    answer -> System.out.printf("  #%d %s%n", answer.getId(), answer.getAnswerText()));
            int number = sc.nextInt();
        }

        context.close();
    }
}
