package ru.tsedrik.schedule.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.tsedrik.service.CourseService;

import java.time.LocalDateTime;

/**
 * Задание по обновлению статуса курсов
 */
public class UpdatingCourseStatusJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(UpdatingCourseStatusJob.class.getName());

    private CourseService courseService;

    public UpdatingCourseStatusJob(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("UpdatingCourseStatusJob start: {}", LocalDateTime.now());
        courseService.changeOldCourseStatus();
        logger.info("UpdatingCourseStatusJob end: {}", LocalDateTime.now());
    }
}
