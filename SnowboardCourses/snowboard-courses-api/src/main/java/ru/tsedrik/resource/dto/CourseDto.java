package ru.tsedrik.resource.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;
import java.util.Set;

/**
 * Класс для передачи параметров создания сущности Course
 */
@ApiModel(description = "Модель для создания курса")
public class CourseDto {

    /**
     * Идентификатор курса
     */
    @ApiModelProperty(value = "Идентификатор курса", example = "1608061807882", allowEmptyValue = true)
    private Long id;

    /**
     * Тип курса, от которого зависит программа
     */
    @ApiModelProperty(value = "Тип курса", example = "AGRESSOR", allowableValues = "CARVING, EXTREMECARVING, AGRESSOR, FLAT, RACE", required = true)
    private String courseType;

    /**
     * Место проведения курса
     */
    @ApiModelProperty(value = "Место проведения курса", allowEmptyValue = true)
    private LocationDto location;

    /**
     * Дата начала курса
     */
    @ApiModelProperty(value = "Дата начала курса", example = "2021-01-10", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate beginDate;

    /**
     * Дата окончания курса
     */
    @ApiModelProperty(value = "Дата окончания курса", example = "2021-01-25", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    /**
     * Количество групп на курсе
     */
    @ApiModelProperty(value = "Количество групп на курсе", example = "2", required = true)
    private int groupCount;

    /**
     * Список групп курса
     */
    @ApiModelProperty(value = "Список групп курса", allowEmptyValue = true)
    private Set<GroupDto> groups;

    public CourseDto(){}

    public CourseDto(Long id, String courseType, LocationDto location, LocalDate beginDate, LocalDate endDate, int groupCount, Set<GroupDto> groups) {
        this.id = id;
        this.courseType = courseType;
        this.location = location;
        this.beginDate = beginDate;
        this.endDate = endDate;
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

    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
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

    public Set<GroupDto> getGroups() {
        return groups;
    }

    public void setGroups(Set<GroupDto> groups) {
        this.groups = groups;
    }

}
