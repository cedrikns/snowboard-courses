package ru.tsedrik.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

/**
 * Класс для передачи параметров поиска сущности Course
 */
public class CourseSearchDto {

    /**
     * Тип курса, от которого зависит программа
     */
    private String courseType;

    /**
     * Дата начала курса
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startTime;

    /**
     * Дата окончания курса
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endTime;

    public CourseSearchDto(){}

    public CourseSearchDto(String courseType, LocalDate startTime, LocalDate endTime) {
        this.courseType = courseType;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }
}
