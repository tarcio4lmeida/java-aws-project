package br.com.siecola.aws_project01.config.local;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.Topic;
import com.amazonaws.services.sns.util.Topics;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Slf4j
@Profile("local")
@Configuration
public class SqsCreateSubscribe{

    public SqsCreateSubscribe(AmazonSNS snsClient, @Qualifier("productEventsTopic") Topic productEventsTopic) {


        AmazonSQS amazonSQS = AmazonSQSClient.builder()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration("http://localhost:4566",
                                Regions.US_EAST_1.getName()))
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .build();

        String productEventsQueueUrl = amazonSQS.createQueue(
                new CreateQueueRequest("product-events")).getQueueUrl();
        log.info("Teste antes productEventsQueueUrl: {}", productEventsQueueUrl);
        Topics.subscribeQueue(snsClient, amazonSQS, productEventsTopic.getTopicArn(), productEventsQueueUrl);
        log.info("productEventsQueueUrl: {}", productEventsQueueUrl);
    }

}
