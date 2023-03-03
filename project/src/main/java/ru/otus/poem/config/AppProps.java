package ru.otus.poem.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("app")
public class AppProps {
    private String serverAddress;
    private String botToken;
    private String botUsername;
    private String chatId;
}
