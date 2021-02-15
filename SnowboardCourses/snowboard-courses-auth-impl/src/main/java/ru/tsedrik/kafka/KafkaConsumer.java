package ru.tsedrik.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.tsedrik.domain.MessageStatus;
import ru.tsedrik.repository.SenderMessageRepository;

import java.util.UUID;

@Component
@Transactional
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class.getName());

    private SenderMessageRepository senderMessageRepository;

    public KafkaConsumer(SenderMessageRepository senderMessageRepository) {
        this.senderMessageRepository = senderMessageRepository;
    }

    @KafkaListener(topics = "${sc.kafka.user.receive-topic}", containerFactory = "stringKafkaListenerContainerFactory")
    public void receiveMessage(String message, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        logger.info("Received message {} with key {}", message, key);

        UUID uuid = UUID.fromString(key);
        if (!senderMessageRepository.existsById(uuid)){
            throw new IllegalArgumentException("There is no message with correlationId = " + uuid);
        }

        MessageStatus status = MessageStatus.SUCCESS;
        try {
            status = MessageStatus.valueOf(message);
            logger.info("Received message body = " + message);
        } catch (Exception e) {
            status = MessageStatus.FAIL;
            throw new RuntimeException(e);
        } finally {
            senderMessageRepository.updateMessageStatusForId(status, uuid);
        }

    }

}
