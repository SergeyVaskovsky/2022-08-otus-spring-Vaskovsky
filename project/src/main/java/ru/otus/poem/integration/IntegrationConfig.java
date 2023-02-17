package ru.otus.poem.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlowDefinition;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;
import ru.otus.poem.bot.Bot;
import ru.otus.poem.model.dto.TelegramMessage;
import ru.otus.poem.service.CommentToTelegramTransformer;


@Configuration
@IntegrationComponentScan
@RequiredArgsConstructor
public class IntegrationConfig {
    private static final int DEFAULT_QUEUE_CAPACITY = 100;
    private static final int DEFAULT_POLLER_PERIOD = 1000;
    private static final String TRANSFORM_METHOD_NAME = "transform";
    private final CommentToTelegramTransformer messageTransformer;
    private final Bot bot;

    @Bean
    public PollableChannel newCommentChannel() {
        return MessageChannels.queue("newCommentChannel", DEFAULT_QUEUE_CAPACITY).get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(DEFAULT_POLLER_PERIOD).get();
    }

    @Bean
    public IntegrationFlow appUserActivityFlow() {
        return f -> f.channel(newCommentChannel())
                .route(Message.class, m -> true
                        , mapping -> mapping.subFlowMapping(true, sub -> sub
                                        .transform(messageTransformer, TRANSFORM_METHOD_NAME)
                                        .handle(m -> {
                                            TelegramMessage message = (TelegramMessage) m.getPayload();
                                            bot.sendComment(message);
                                        })
                                )
                                .subFlowMapping(false, IntegrationFlowDefinition::nullChannel)
                );
    }
}
