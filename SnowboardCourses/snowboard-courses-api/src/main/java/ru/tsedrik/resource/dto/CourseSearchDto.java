package ru.tsedrik.resource.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;

/**
 * Класс для передачи параметров поиска сущности Course
 */
@ApiModel(description = "Модель для поиска по курсам")
public class CourseSearchDto {

    /**
     * Тип курса, от которого зависит программа
     */
    @ApiModelProperty(value = "Тип курса", example = "AGRESSOR", allowableValues = "CARVING, EXTREMECARVING, AGRESSOR, FLAT, RACE", allowEmptyValue = true)
    private String courseType;

    /**
     * Дата начала курса
     */
    @ApiModelProperty(value = "Дата начала курса", example = "2021-01-10", allowEmptyValue = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startTime;

    /**
     * Дата окончания курса
     */
    @ApiModelProperty(value = "Дата окончания курса", example = "2021-01-25", allowEmptyValue = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endTime;

    public CourseSearchDto(){}

    public CourseSearchDto(String courseType, LocalDate startTime, LocalDate endTime) {
        this.courseType = courseType;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
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

}
