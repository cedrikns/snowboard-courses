package ru.tsedrik.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.tsedrik.domain.MessageStatus;
import ru.tsedrik.repository.SenderMessageRepository;

/**
 * Класс для фоновых задач, которые будуту запускаться по cron-расписанию
 */
@Component
@ConditionalOnProperty(prefix = "scheduling.cron", name = {"enabled"}, matchIfMissing = false)
public class CronSchedule {

    private static final Logger logger = LoggerFactory.getLogger(CronSchedule.class.getName());

    private SenderMessageRepository senderMessageRepository;

    public CronSchedule(SenderMessageRepository senderMessageRepository) {
        this.senderMessageRepository = senderMessageRepository;
    }

    @Scheduled(cron = "${scheduling.cron.expression}")
    @Transactional
    public void deleteSuccessMessages() {
        logger.info("deleteSuccessMessages - start ");
        senderMessageRepository.deleteAllByStatus(MessageStatus.SUCCESS);
        logger.info("deleteSuccessMessages - end ");
    }
}
