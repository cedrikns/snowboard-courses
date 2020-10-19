package ru.tsedrik.model;

import java.time.LocalDate;
import java.util.List;

public class Course implements Identifired<Integer>{
    private int id;
    private CourseType courseType;
    private CourseLocation courseLocation;
    private LocalDate startTime;
    private LocalDate endTime;
    private int groupCount;
    private List<Group> groups;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseType=" + courseType +
                ", courseLocation=" + courseLocation +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
