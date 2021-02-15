package ru.tsedrik.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.tsedrik.domain.MessageStatus;
import ru.tsedrik.domain.SenderMessage;
import ru.tsedrik.repository.SenderMessageRepository;

import java.util.UUID;

/**
 * Класс для отправки сообщений в топик
 */
@Component
@Transactional
public class KafkaSender {

    private static final Logger logger = LoggerFactory.getLogger(KafkaSender.class.getName());

    private final KafkaTemplate<String, String> stringKafkaTemplate;

    /**
     * Объект для управления персистентным состоянием объектов типа SenderMessage
     */
    private SenderMessageRepository senderMessageRepository;

    public KafkaSender(KafkaTemplate<String, String> stringKafkaTemplate, SenderMessageRepository senderMessageRepository) {
        this.stringKafkaTemplate = stringKafkaTemplate;
        this.senderMessageRepository = senderMessageRepository;
    }

    public void sendMessage(final String topicName, final KafkaUserDto kafkaUserDto) {
        logger.info("Sending message {} to topic - {}", kafkaUserDto, topicName);
        UUID uuid = UUID.randomUUID();
        String body = "";

        try{
            body = new ObjectMapper().writeValueAsString(kafkaUserDto);
        } catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }

        SenderMessage senderMessage = new SenderMessage(
                uuid,
                body,
                MessageStatus.SEND
        );

        senderMessageRepository.save(senderMessage);

        stringKafkaTemplate.send(topicName, uuid.toString(), body);
    }

}
