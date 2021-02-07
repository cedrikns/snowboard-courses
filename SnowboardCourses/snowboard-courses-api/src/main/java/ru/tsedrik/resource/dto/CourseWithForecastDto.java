package ru.tsedrik.resource.dto;

import ru.tsedrik.invoker.Forecast;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

/**
 * Класс для вывода информации о погоде во время курса
 */
public class CourseWithForecastDto extends CourseDto{

    /**
     * Погода на даты курса
     */
    private Map<LocalDate, Forecast> forecast;

    public CourseWithForecastDto() {
    }

    public CourseWithForecastDto(Map<LocalDate, Forecast> forecast) {
        this.forecast = forecast;
    }

    public CourseWithForecastDto(Long id, String courseType, LocationDto location, LocalDate beginDate, LocalDate endDate, int groupCount, Set<GroupDto> groups, Map<LocalDate, Forecast> forecast) {
        super(id, courseType, location, beginDate, endDate, groupCount, groups);
        this.forecast = forecast;
    }

    public CourseWithForecastDto(CourseDto courseDto, Map<LocalDate, Forecast> forecast){
        super(courseDto.getId(), courseDto.getCourseType(),
                courseDto.getLocation(), courseDto.getBeginDate(),
                courseDto.getEndDate(), courseDto.getGroupCount(),
                courseDto.getGroups());
        this.forecast = forecast;
    }

    public Map<LocalDate, Forecast> getForecast() {
        return forecast;
    }

    public void setForecast(Map<LocalDate, Forecast> forecast) {
        this.forecast = forecast;
    }
}
