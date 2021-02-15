package ru.tsedrik.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.tsedrik.domain.ReceiverMessage;
import ru.tsedrik.repository.ReceiverMessageRepository;

import java.util.UUID;

@Component
@Transactional
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class.getName());

    private ReceiverMessageRepository receiverMessageRepository;
    private KafkaSender kafkaSender;

    @Value("${sc.kafka.user.receive-topic}")
    private String sendUserTopic;

    public KafkaConsumer(ReceiverMessageRepository receiverMessageRepository, KafkaSender kafkaSender) {
        this.receiverMessageRepository = receiverMessageRepository;
        this.kafkaSender = kafkaSender;
    }

    @KafkaListener(topics = "${sc.kafka.user.send-topic}", containerFactory = "stringKafkaListenerContainerFactory")
    public void receiveMessage(String message, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        logger.info("Received message {} with key {}", message, key);

        UUID uuid = null;
        String status = "FAIL";

        try {
            uuid = UUID.fromString(key);
            ReceiverMessage receiverMessage = new ReceiverMessage(uuid, message);
            receiverMessageRepository.save(receiverMessage);
            status = "SUCCESS";
        } catch (Exception e){
            status = "FAIL";
            throw new RuntimeException(e);
        } finally {
            kafkaSender.sendMessageString(sendUserTopic, key, status);
        }

    }

}
