package ru.tsedrik.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.tsedrik.domain.MessageStatus;
import ru.tsedrik.repository.SenderMessageRepository;

import javax.jms.Message;
import javax.jms.JMSException;
import java.util.UUID;

/**
 * Класс для получения сообщений из очереди и дальнейшей их обработки
 */
@Component
@Transactional
public class JMSMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(JMSMessageListener.class.getName());

    private SenderMessageRepository senderMessageRepository;

    public JMSMessageListener(SenderMessageRepository senderMessageRepository) {
        this.senderMessageRepository = senderMessageRepository;
    }

    @JmsListener(destination = "${sc.jms.user.receive-queue}")
    public void receiveMessage(Message message, String body) {
        logger.info("Received message {}", message);

        try {
            UUID uuid = UUID.fromString(message.getJMSCorrelationID());
            if (!senderMessageRepository.existsById(uuid)){
                throw new IllegalArgumentException("There is no message with correlationId = " + uuid);
            }

            MessageStatus status = MessageStatus.SUCCESS;
            try {
                status = MessageStatus.valueOf(body.toUpperCase());
                logger.info("Received message body = " + status);
            } catch (Exception e) {
                status = MessageStatus.FAIL;
                throw new RuntimeException(e);
            } finally {
                senderMessageRepository.updateMessageStatusForId(status, uuid);
            }

        } catch (JMSException e){
            throw new RuntimeException(e);
        }

    }
}
