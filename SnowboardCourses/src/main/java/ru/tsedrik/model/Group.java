package ru.tsedrik.model;

import java.util.Set;

public class Group implements Identifired<Integer> {
    private int id;
    private Person instructor;
    private int totalNumberOfPlaces;
    private int availableNumberOfPlaces;
    private Set<Person> students;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", instructor=" + instructor +
                ", students=" + students +
                '}';
    }
}
