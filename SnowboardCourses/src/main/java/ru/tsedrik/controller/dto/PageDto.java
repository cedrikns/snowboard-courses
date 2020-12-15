package ru.tsedrik.controller.dto;

import java.util.List;

/**
 * Класс для вывода результатов поиска постранично
 */
public class PageDto <T>{

    /**
     * Данные, найденные в результате поиска
     */
    private List<T> data;

    /**
     * Общее количество найденных данных
     */
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
}
