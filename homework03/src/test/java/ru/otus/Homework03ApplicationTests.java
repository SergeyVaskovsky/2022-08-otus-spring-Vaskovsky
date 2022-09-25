package ru.otus;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.config.AppConfig;

@SpringBootTest(classes = {AppConfig.class})
@ImportAutoConfiguration(MessageSourceAutoConfiguration.class)
public class Homework03ApplicationTests {

	@Test
	void contextLoads() {
	}
}

