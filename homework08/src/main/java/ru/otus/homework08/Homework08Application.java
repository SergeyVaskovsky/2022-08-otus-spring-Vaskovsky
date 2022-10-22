package ru.otus.homework08;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongock
@SpringBootApplication
@EnableConfigurationProperties
public class Homework08Application {

	public static void main(String[] args) {
		SpringApplication.run(Homework08Application.class, args);
	}

}
