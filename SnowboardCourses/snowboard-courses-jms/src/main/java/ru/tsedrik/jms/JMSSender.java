package ru.tsedrik.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Класс для отправки сообщений в очередь
 */
@Component
public class JMSSender {

    private static final Logger logger = LoggerFactory.getLogger(JMSSender.class.getName());

    private final JmsTemplate jmsTemplate;

    public JMSSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessageString(final String queueName, final UUID correlationId, final String status) {
        logger.info("Sending message {} with correlation id {} to queue - {}", status, correlationId, queueName);

        jmsTemplate.convertAndSend(queueName, status, message -> {
                    message.setJMSCorrelationID(correlationId.toString());
                    return message;
                }
        );
    }
}
