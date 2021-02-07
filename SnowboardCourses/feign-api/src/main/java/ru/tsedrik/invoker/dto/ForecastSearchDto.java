package ru.tsedrik.invoker.dto;

import java.time.LocalDate;

/**
 * Класс для передачи параметров поиска погоды на период
 */
public class ForecastSearchDto {

    /**
     * Наименование локации
     */
    private String name;

    /**
     * Начало периода
     */
    private LocalDate beginDate;

    /**
     * Конец периода
     */
    private LocalDate endDate;

    public ForecastSearchDto() {
    }

    public ForecastSearchDto(String name, LocalDate beginDate, LocalDate endDate) {
        this.name = name;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
