package br.com.siecola.aws_project02.service;


import br.com.siecola.aws_project02.model.Envelope;
import br.com.siecola.aws_project02.model.ProductEvent;
import br.com.siecola.aws_project02.model.SnsMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductEventConsumer {

    private final ObjectMapper objectMapper;

    //private ProductEventLogRepository productEventLogRepository;

    @JmsListener(destination = "${aws.sqs.queue.product.events.name}")
    public void receiveProductEvent(TextMessage textMessage) throws JMSException, IOException {

        SnsMessage snsMessage = objectMapper.readValue(textMessage.getText(), SnsMessage.class);

        Envelope envelope = objectMapper.readValue(snsMessage.getMessage(), Envelope.class);

        ProductEvent productEvent = objectMapper.readValue(envelope.getData(), ProductEvent.class);

        log.info("Product event received - Event: {} - ProductId: {} - MessageId: {}", envelope.getEventType(),
                productEvent.getId(), snsMessage.getMessageId());

    }
}
