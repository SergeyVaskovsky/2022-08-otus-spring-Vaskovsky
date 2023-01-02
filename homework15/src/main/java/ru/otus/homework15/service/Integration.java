package ru.otus.homework15.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.BridgeTo;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.Message;
import ru.otus.homework15.model.*;

import java.util.stream.Collectors;

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
    //@BridgeTo(value = "outputChannel")
    QueueChannel produceCarBodyChannel() { return new QueueChannel(); }

    @Bean
    //@BridgeTo(value = "outputChannel")
    QueueChannel produceEngineChannel() { return new QueueChannel(); }

    @Bean
    //@BridgeTo(value = "outputChannel")
    QueueChannel produceOptionsChannel() { return new QueueChannel(); }

    @Bean
    public IntegrationFlow factoryFlow() {
        return IntegrationFlows.from( "inputChannel" )
                .split("orderSplitter", "splitItem")
                .publishSubscribeChannel(subscription ->
                        subscription
                                .subscribe(subflow -> subflow
                                        .<Part> filter(part -> part.getDescriptor().equals(Part.Descriptor.CAR_BODY_NAME))
                                        .handle("carBodyService", "produce")
                                        .channel("produceCarBodyChannel"))
                                .subscribe(subflow -> subflow
                                        .<Part> filter(part -> part.getDescriptor().equals(Part.Descriptor.ENGINE))
                                        .handle("engineService", "produce")
                                        .channel("produceEngineChannel"))
                                .subscribe(subflow -> subflow
                                        .<Part> filter(part -> part.getDescriptor().equals(Part.Descriptor.OPTIONS))
                                        .handle("optionsService", "produce")
                                        .channel("produceOptionsChannel"))
                                )
                .aggregate(a -> a.releaseStrategy(g -> g.size() >= 3)
                                .outputProcessor(group -> {
                                    CarBody carBody = (CarBody) produceCarBodyChannel().receive().getPayload();
                                    Engine engine = (Engine) produceEngineChannel().receive().getPayload();
                                    Options options = (Options) produceOptionsChannel().receive().getPayload();
                                    return new Car(carBody, engine, options);
                                }))
                .channel( "outputChannel" )
                .get();
    }
}
