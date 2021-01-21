package ru.tsedrik.resource.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Класс для вывода результатов поиска постранично
 */
@ApiModel(description = "Модель ответа для поиска по объекатам")
public class PageDto <T>{

    /**
     * Данные, найденные в результате поиска
     */
    @ApiModelProperty(value = "Возращаемые данные по поиску", required = true)
    private List<T> data;

    /**
     * Общее количество найденных данных
     */
    @ApiModelProperty(value = "Общее количество найденных данных", example = "14", required = true)
    private long totalCount;

    public PageDto(List<T> data, long totalCount) {
        this.data = data;
        this.totalCount = totalCount;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "PageDto{" +
                "data=" + data +
                ", totalCount=" + totalCount +
                '}';
    }
}
