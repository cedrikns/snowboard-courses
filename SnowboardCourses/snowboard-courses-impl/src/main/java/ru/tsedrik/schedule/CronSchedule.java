package ru.tsedrik.schedule;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tsedrik.service.CourseService;

/**
 * Класс для фоновых задач, которые будуту запускаться по cron-расписанию
 */
@Component
@ConditionalOnProperty(prefix = "scheduling.cron", name = {"enabled"}, matchIfMissing = false)
public class CronSchedule {

    private CourseService courseService;

    public CronSchedule(CourseService courseService) {
        this.courseService = courseService;
    }

    @Scheduled(cron = "${scheduling.cron.expression}")
    public void changeOldCourseStatus() {

        courseService.changeOldCourseStatus();
    }
}
