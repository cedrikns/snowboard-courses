package ru.tsedrik.resource.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Set;


@ApiModel(description = "Модель для отображения групп в курсе")
public class GroupDto {
    /**
     * Идентификатор группы
     */
    @ApiModelProperty(value = "Идентификатор группы", example = "1608061807883", required = true)
    private Long id;

    /**
     * Инструктор группы
     */
    @ApiModelProperty(value = "Инструктор группы", allowEmptyValue = true)
    private PersonDto instructor;

    /**
     * Общее количество мест в группе
     */
    @ApiModelProperty(value = "Общее количество мест в группе", example = "6", required = true)
    private int totalNumberOfPlaces;

    /**
     * Количество оставшихся свободных мест в группе
     */
    @ApiModelProperty(value = "Количество оставшихся свободных мест в группе", example = "3", required = true)
    private int availableNumberOfPlaces;

    /**
     * Список участников, которые будут обучатья в группе
     */
    @ApiModelProperty(value = "Список участников, которые будут обучатья в группе", allowEmptyValue = true)
    private Set<PersonDto> students;

    public GroupDto() {}

    public GroupDto(Long id, PersonDto instructor, int totalNumberOfPlaces, int availableNumberOfPlaces, Set<PersonDto> students) {
        this.id = id;
        this.instructor = instructor;
        this.totalNumberOfPlaces = totalNumberOfPlaces;
        this.availableNumberOfPlaces = availableNumberOfPlaces;
        this.students = students;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonDto getInstructor() {
        return instructor;
    }

    public void setInstructor(PersonDto instructor) {
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

    public Set<PersonDto> getStudents() {
        return students;
    }

    public void setStudents(Set<PersonDto> students) {
        this.students = students;
    }

}
