package ru.tsedrik.schedule.trigger;

import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.stereotype.Component;
import ru.tsedrik.schedule.job.UpdatingCourseStatusJob;

/**
 * Конфигурационный файл за настройки запуска заданий по cron-расписанию
 */
@Component
@ConditionalOnProperty(prefix = "scheduling.cron", name = {"enabled"}, matchIfMissing = false)
public class CronTriggerConfig extends AbstractTrigger{

    @Bean
    public JobDetailFactoryBean updatingCourseStatusCronJobDetail() {
        return createJobDetail(UpdatingCourseStatusJob.class, UpdatingCourseStatusJob.class.getSimpleName(),
                "Задание для запуска по cron обновления статуса курсов.");
    }

    @Bean(name = "updatingCourseStatusCronTrigger")
    public CronTriggerFactoryBean updatingCourseStatusCronTrigger(@Qualifier("updatingCourseStatusCronJobDetail") JobDetail jobDetail,
                                              @Value("${scheduling.cron.updating-course-status.expression}") String cron) {
        return createCronTrigger(jobDetail, cron, "updatingCourseStatusCronTrigger",
                "Триггер для запуска по cron обновления статуса курсов.");
    }
}
