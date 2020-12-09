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
public class Course implements Identifired<Long>{

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
     * Место проведения курса
     */
    @ManyToOne
    @JoinColumn(name = "location_id")
    private CourseLocation courseLocation;

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

    public Course(Long id, String courseType, CourseLocation courseLocation, LocalDate beginDate, LocalDate endDate, int groupCount) {
        this.id = id;
        this.courseType = CourseType.valueOf(courseType.toUpperCase());
        this.courseLocation = courseLocation;
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

    public CourseLocation getCourseLocation() {
        return courseLocation;
    }

    public void setCourseLocation(CourseLocation courseLocation) {
        this.courseLocation = courseLocation;
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
                ", startTime=" + beginDate +
                ", endTime=" + endDate +
                ", groups=" + groups +
                '}';
    }
}
