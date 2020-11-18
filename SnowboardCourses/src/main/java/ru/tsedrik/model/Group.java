package ru.tsedrik.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Group представляет собой группу определенного курса.
 * Каждая группа состоит из определенного количества участников
 * и одного инструктора.
 */
public class Group implements Identifired<Long> {

    /**
     * Идентификатор группы
     */
    private Long id;

    /**
     * Идентификатор курса, для которого создана данная группа
     */
    private Long courseId;

    /**
     * Инструктор группы
     */
    private Person instructor;

    /**
     * Общее количество мест в группе
     */
    private int totalNumberOfPlaces;

    /**
     * Количество оставшихся свободных мест в группе
     */
    private int availableNumberOfPlaces;

    /**
     * Список участников, которые будут обучатья в группе
     */
    private List<Person> students;

    public Group() {}

    public Group(Long id, Long courseId, Integer totalNumberOfPlaces){
        this.id = id;
        this.courseId = courseId;
        this.totalNumberOfPlaces = this.availableNumberOfPlaces = totalNumberOfPlaces;
        students = new ArrayList<>();
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

    public List<Person> getStudents() {
        return students;
    }

    public void setStudents(List<Person> students) {
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
