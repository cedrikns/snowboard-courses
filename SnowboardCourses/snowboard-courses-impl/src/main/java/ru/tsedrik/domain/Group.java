package ru.tsedrik.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Group представляет собой группу определенного курса.
 * Каждая группа состоит из определенного количества участников
 * и одного инструктора.
 */
@Entity
@Table(schema = "sc_core", name = "groups")
public class Group extends CreateAtIdentified implements Identifired<Long> {

    /**
     * Идентификатор группы
     */
    @Id
    private Long id;

    /**
     * Идентификатор курса, для которого создана данная группа
     */
    @Column(name = "course_id", nullable = false)
    private Long courseId;

    /**
     * Инструктор группы
     */

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person instructor;

    /**
     * Общее количество мест в группе
     */
    @Column(name = "places_num_total", nullable = false)
    private int totalNumberOfPlaces;

    /**
     * Количество оставшихся свободных мест в группе
     */
    @Column(name = "places_num_available", nullable = false)
    private int availableNumberOfPlaces;

    /**
     * Список участников, которые будут обучатья в группе
     */
    @ManyToMany(cascade={CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(schema = "sc_core", name="group_person", joinColumns=@JoinColumn(name="group_id"), inverseJoinColumns=@JoinColumn(name="person_id"))
    private Set<Person> students;

    public Group() {}

    public Group(Long id, Long courseId, Integer totalNumberOfPlaces){
        this.id = id;
        this.courseId = courseId;
        this.totalNumberOfPlaces = this.availableNumberOfPlaces = totalNumberOfPlaces;
        students = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getInstructor() {
        return instructor;
    }

    public void setInstructor(Person instructor) {
        this.instructor = instructor;
    }

    public int getTotalNumberOfPlaces() {
        return totalNumberOfPlaces;
    }

    public void setTotalNumberOfPlaces(int totalNumberOfPlaces) {
        this.totalNumberOfPlaces = totalNumberOfPlaces;
    }

    public int getAvailableNumberOfPlaces() {
        return availableNumberOfPlaces;
    }

    public void setAvailableNumberOfPlaces(int availableNumberOfPlaces) {
        this.availableNumberOfPlaces = availableNumberOfPlaces;
    }

    public Set<Person> getStudents() {
        return students;
    }

    public void setStudents(Set<Person> students) {
        this.students = students;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", instructor=" + instructor +
                ", students=" + students +
                '}';
    }
}
