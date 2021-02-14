package ru.tsedrik.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.tsedrik.domain.ReceiverMessage;
import ru.tsedrik.repository.ReceiverMessageRepository;

import javax.jms.Message;
import java.util.UUID;

/**
 * Класс для получения сообщений из очереди и дальнейшей их обработки
 */
@Component
@Transactional
public class JMSMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(JMSMessageListener.class.getName());

    private ReceiverMessageRepository receiverMessageRepository;
    private JMSSender jmsSender;

    @Value("${sc.jms.user.receive-queue}")
    private String sendUserQueue;

    public JMSMessageListener(ReceiverMessageRepository receiverMessageRepository, JMSSender jmsSender) {
        this.receiverMessageRepository = receiverMessageRepository;
        this.jmsSender = jmsSender;
    }

    @JmsListener(destination = "${sc.jms.user.send-queue}")
    public void receiveMessage(Message message, String body) {
        logger.info("Received message {}", message);
        logger.info("Received body {}", body);
        UUID uuid = null;
        String status = "FAIL";
        try {
            uuid = UUID.fromString(message.getJMSCorrelationID());
            ReceiverMessage receiverMessage = new ReceiverMessage(uuid, body);
            receiverMessageRepository.save(receiverMessage);
            status = "SUCCESS";
        } catch (Exception e){
            status = "FAIL";
            throw new RuntimeException(e);
        } finally {
            jmsSender.sendMessageString(sendUserQueue, uuid, status);
        }

    }
}
