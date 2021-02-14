package ru.tsedrik.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import ru.tsedrik.domain.MessageStatus;
import ru.tsedrik.domain.SenderMessage;
import ru.tsedrik.repository.SenderMessageRepository;

import java.util.UUID;

/**
 * Класс для отправки сообщений в очередь
 */
@Component
public class JMSSender {

    private static final Logger logger = LoggerFactory.getLogger(JMSSender.class.getName());

    private final JmsTemplate jmsTemplate;

    /**
     * Объект для управления персистентным состоянием объектов типа SenderMessage
     */
    private SenderMessageRepository senderMessageRepository;

    public JMSSender(JmsTemplate jmsTemplate, SenderMessageRepository senderMessageRepository) {
        this.jmsTemplate = jmsTemplate;
        this.senderMessageRepository = senderMessageRepository;
    }

    public void sendMessageJMSUserDto(final String queueName, final JMSUserDto jmsUserDto) {
        logger.info("Sending message {} to queue - {}", jmsUserDto, queueName);

        UUID uuid = UUID.randomUUID();
        String body = "";

        try{
            body = new ObjectMapper().writeValueAsString(jmsUserDto);
        } catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }

        SenderMessage senderMessage = new SenderMessage(
                uuid,
                body,
                MessageStatus.SEND
        );

        senderMessageRepository.save(senderMessage);

        jmsTemplate.convertAndSend(queueName, body, message -> {
                    message.setJMSCorrelationID(uuid.toString());
                    return message;
                }
        );
    }
}
