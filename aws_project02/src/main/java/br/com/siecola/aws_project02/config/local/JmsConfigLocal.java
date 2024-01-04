package br.com.siecola.aws_project02.config.local;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import jakarta.jms.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import software.amazon.awssdk.endpoints.Endpoint;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.endpoints.SqsEndpointProvider;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

@Configuration
@EnableJms
@Profile("local")
public class JmsConfigLocal {

    private SQSConnectionFactory sqsConnectionFactory;
    public static final String ENDPOINT = "http://localhost:4566";

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        sqsConnectionFactory = new SQSConnectionFactory(
                new ProviderConfiguration(),
                SqsClient.builder()
                        .region(Region.SA_EAST_1)
                        .endpointProvider(getEndpointProvider())
                        .build());

        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(sqsConnectionFactory);
        factory.setDestinationResolver(new DynamicDestinationResolver());
        factory.setConcurrency("2");
        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);

        return factory;
    }

    public SqsEndpointProvider getEndpointProvider() {
        return endpointParams ->
                CompletableFuture.completedFuture(
                        Endpoint.builder()
                                .url(URI.create(ENDPOINT))
                                .build());
    }

}