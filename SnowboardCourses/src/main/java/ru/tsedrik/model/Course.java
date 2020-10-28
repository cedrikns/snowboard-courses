package ru.tsedrik.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Course представляет собой курс, на который будут записываться люди.
 * Каждый объект данного класса будет характеризоваться:
 * - названием курса
 * - местом проведения
 * - датой проведения
 * - набором групп обучающихся
 */
public class Course implements Identifired<Long>{

    /**
     * Идентификатор курса
     */
    private Long id;

    /**
     * Тип курса, от которого зависит программа
     */
    private CourseType courseType;

    /**
     * Место проведения курса
     */
    private CourseLocation courseLocation;

    /**
     * Дата начала курса
     */
    private LocalDate startTime;

    /**
     * Дата окончания курса
     */
    private LocalDate endTime;

    /**
     * Количество групп на курсе
     */
    private int groupCount;

    /**
     * Список групп курса
     */
    private List<Group> groups;

    public Course(Long id, String courseType, CourseLocation courseLocation, LocalDate startTime, LocalDate endTime, int groupCount) {
        this.id = id;
        this.courseType = CourseType.valueOf(courseType.toUpperCase());
        this.courseLocation = courseLocation;
        this.startTime = startTime;
        this.endTime = endTime;
        this.groupCount = groupCount;
        this.groups = new ArrayList<>();
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) {
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

    public Group getAvailableGroup(){
        return groups.stream().filter(g -> g.getAvailableNumberOfPlaces() > 0).findFirst().get();
    }

    public boolean isAvailableGroupExist(){
        return groups.stream().anyMatch(g -> g.getAvailableNumberOfPlaces() > 0);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseType=" + courseType +
                ", courseLocation=" + courseLocation +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", groups=" + groups +
                '}';
    }
}
