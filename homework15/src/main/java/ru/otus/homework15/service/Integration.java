package ru.otus.homework15.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;

@IntegrationComponentScan
@ComponentScan
@Configuration
@EnableIntegration
public class Integration {

    @Bean
    public QueueChannel inputChannel() {
        return MessageChannels.queue( 10 ).get();
    }

    @Bean
    public PublishSubscribeChannel outputChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate( 100 ).maxMessagesPerPoll( 2 ).get();
    }

    @Bean
    public IntegrationFlow natureFlow() {
        return IntegrationFlows.from( "inputChannel" )
                .handle( "sleepingService", "sleep" )
                .handle( "magicalTurningInto", "turnInto" )
                .channel( "outputChannel" )
                .get();
    }
}
