package br.com.siecola.aws_project01.service;

import br.com.siecola.aws_project01.enums.EventType;
import br.com.siecola.aws_project01.model.Product;
import br.com.siecola.aws_project01.model.sns.Envelope;
import br.com.siecola.aws_project01.model.sns.ProductEvent;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.Topic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ProductSnsPublisher {
    private static final Logger LOG = LoggerFactory.getLogger(ProductSnsPublisher.class);
    private AmazonSNS snsClient;
    private Topic productTopicEvent;
    private ObjectMapper objectMapper;

    public ProductSnsPublisher(AmazonSNS snsClient, @Qualifier("productEventsTopic") Topic productTopicEvent, ObjectMapper objectMapper) {
        this.snsClient = snsClient;
        this.productTopicEvent = productTopicEvent;
        this.objectMapper = objectMapper;
    }

    public void publishProductEvent(Product product, EventType eventType, String userName) {
        try {
            ProductEvent productEvent = ProductEvent.builder()
                    .id(product.getId())
                    .code(product.getCode())
                    .userName(userName)
                    .build();

            Envelope envelope = Envelope.builder()
                    .eventType(eventType)
                    .data(objectMapper.writeValueAsString(productEvent))
                    .build();

            PublishResult publishResult = snsClient.publish(productTopicEvent.getTopicArn(), objectMapper.writeValueAsString(envelope));

            LOG.info("ProductEvent sent Event: {} - ProductId: {} - MessageId: {}",
                    envelope.getEventType(),
                    productEvent.getId(),
                    publishResult.getMessageId());

        } catch (JsonProcessingException e) {
            LOG.error("Failed to create product event message");

        }
    }
}
