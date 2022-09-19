package ru.otus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.config.AppConfig;
import ru.otus.service.TestService;

@SpringBootApplication
@EnableConfigurationProperties(AppConfig.class)
public class Homework03Application {

	public static void main(String[] args) {
		TestService service = SpringApplication
				.run(Homework03Application.class, args)
				.getBean(TestService.class);
		service.test();
	}

}
