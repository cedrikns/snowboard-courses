package ru.tsedrik.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.tsedrik.model.CourseLocation;
import ru.tsedrik.model.Group;

import java.time.LocalDate;
import java.util.List;

public class CourseDto {

    /**
     * Идентификатор курса
     */
    private Long id;

    /**
     * Тип курса, от которого зависит программа
     */
    private String courseType;

    /**
     * Место проведения курса
     */
    private CourseLocation courseLocation;

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

    /**
     * Количество групп на курсе
     */
    private int groupCount;

    /**
     * Список групп курса
     */
    private List<Group> groups;

    public CourseDto(){}

    public CourseDto(Long id, String courseType, CourseLocation courseLocation, LocalDate startTime, LocalDate endTime, int groupCount, List<Group> groups) {
        this.id = id;
        this.courseType = courseType;
        this.courseLocation = courseLocation;
        this.startTime = startTime;
        this.endTime = endTime;
        this.groupCount = groupCount;
        this.groups = groups;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public CourseLocation getCourseLocation() {
        return courseLocation;
    }

    public void setCourseLocation(CourseLocation courseLocation) {
        this.courseLocation = courseLocation;
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

    public int getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(int groupCount) {
        this.groupCount = groupCount;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
