package ru.tsedrik.schedule.trigger;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

/**
 * AbstractTrigger.
 * Служит для описания базового функционала для создания тригера на {@link Job} в кластерной системе.
 * Позволяет создать {@link JobDetailFactoryBean}, а также тригеры по крону {@link CronTriggerFactoryBean}
 * и таймауту {@link SimpleTriggerFactoryBean}. Для этих целей необходимо унаследовать данный класс,
 * в котором настроить 2 основных бина:  {@link JobDetailFactoryBean} саму задачу и правило для ее обработки.
 * Создание каких-либо шедулеров при горизонтальном маштабировании запрещено.
 * Только на основании кластера с использованием данного триггера.
 * Создавать можно только через данный способ. Иначе при кластеризации(масштабировании) сервиса будут
 * получены дубликаты срабатывания джобов в системе.
 */
public class AbstractTrigger {

    /**
     * Создаёт{@link JobDetailFactoryBean} для запуска в Spring Quartz
     *
     * @param jobClass    класс являющийся реализацией {@link Job}, на который будет создана джоба.
     * @param name        имя джобы
     * @param description описание джобы
     * @return {@link JobDetailFactoryBean}
     */
    public JobDetailFactoryBean createJobDetail(Class<? extends Job> jobClass,
                                                String name, String description) {
        JobDetailFactoryBean detailFactoryBean = new JobDetailFactoryBean();
        detailFactoryBean.setJobClass(jobClass);
        detailFactoryBean.setName(name);
        detailFactoryBean.setDescription(description);
        detailFactoryBean.setDurability(true);
        return detailFactoryBean;
    }

    /**
     * Создаёт крон правило {@link CronTriggerFactoryBean} для запуска в Spring Quartz
     *
     * @param jobDetail      {@link JobDetail}, на который будет создан триггер.
     * @param cronExpression крон выражения для задания правила запуска триггера.
     * @param name           имя триггера.
     * @param description    описание триггера.
     * @return {@link CronTriggerFactoryBean}
     */
    public CronTriggerFactoryBean createCronTrigger(JobDetail jobDetail, String cronExpression,
                                                    String name, String description) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setCronExpression(cronExpression);
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        factoryBean.setDescription(description);
        factoryBean.setName(name);
        return factoryBean;
    }

    /**
     * Создаёт правило {@link SimpleTriggerFactoryBean} для запуска в Spring Quartz
     *
     * @param jobDetail   {@link JobDetail}, на который будет создан триггер.
     * @param interval    интервал, с которым выполняется джоба
     * @param name        имя триггера
     * @param description описание триггера
     * @return {@link SimpleTriggerFactoryBean}
     */
    public SimpleTriggerFactoryBean createTrigger(JobDetail jobDetail, Long interval,
                                                  String name, String description) {
        SimpleTriggerFactoryBean triggerFactoryBean = new SimpleTriggerFactoryBean();
        triggerFactoryBean.setJobDetail(jobDetail);
        triggerFactoryBean.setStartDelay(interval);
        triggerFactoryBean.setRepeatInterval(interval);
        triggerFactoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        triggerFactoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
        triggerFactoryBean.setName(name);
        triggerFactoryBean.setDescription(description);
        return triggerFactoryBean;
    }
}
