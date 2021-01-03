package ru.tsedrik.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Course представляет собой курс, на который будут записываться люди.
 * Каждый объект данного класса будет характеризоваться:
 * - названием курса
 * - местом проведения
 * - датой проведения
 * - набором групп обучающихся
 */
@Entity
@Table(name = "course")
public class Course extends CreateAtIdentified implements Identifired<Long> {

    /**
     * Идентификатор курса
     */
    @Id
    private Long id;

    /**
     * Тип курса, от которого зависит программа
     */
    @Column(name = "course_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseType courseType;

    /**
     * Статус курса, от которого зависит можно ли на него записаться
     */
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseStatus courseStatus;

    /**
     * Место проведения курса
     */
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    /**
     * Дата начала курса
     */
    @Column(name = "begin_date", nullable = false)
    private LocalDate beginDate;

    /**
     * Дата окончания курса
     */
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    /**
     * Количество групп на курсе
     */
    @Column(name = "max_groups_num", nullable = false)
    private int groupCount;

    /**
     * Список групп курса
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    private Set<Group> groups;

    public Course(){}

    public Course(Long id, String courseType, Location location, LocalDate beginDate, LocalDate endDate, int groupCount, CourseStatus courseStatus) {
        this.id = id;
        this.courseType = CourseType.valueOf(courseType.toUpperCase());
        this.courseStatus = courseStatus;
        this.location = location;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.groupCount = groupCount;
        this.groups = new HashSet<>();
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(int groupCount) {
        this.groupCount = groupCount;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public CourseStatus getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(CourseStatus courseStatus) {
        this.courseStatus = courseStatus;
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
                ", location=" + location +
                ", startTime=" + beginDate +
                ", endTime=" + endDate +
                ", groups=" + groups +
                '}';
    }
}
