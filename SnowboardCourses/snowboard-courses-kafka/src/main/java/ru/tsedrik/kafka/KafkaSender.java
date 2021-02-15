package ru.tsedrik.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Класс для отправки сообщений в топик
 */
@Component
@Transactional
public class KafkaSender {

    private static final Logger logger = LoggerFactory.getLogger(KafkaSender.class.getName());

    private final KafkaTemplate<String, String> stringKafkaTemplate;

    public KafkaSender(KafkaTemplate<String, String> stringKafkaTemplate) {
        this.stringKafkaTemplate = stringKafkaTemplate;
    }

    public void sendMessageString(final String topicName, final String key, final String status) {
        logger.info("Sending message {} with id {} to topic - {}", status, key, topicName);
        stringKafkaTemplate.send(topicName, key, status);
    }

}
