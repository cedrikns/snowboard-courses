package ru.tsedrik.resource.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Класс для передачи параметров поиска сущности Location
 */
@ApiModel(description = "Модель для поиска по местам проведения курсов")
public class LocationSearchDto {

    /**
     * Название места проведения
     */
    @ApiModelProperty(value = "Название места проведения курса", example = "Шерегеш", allowEmptyValue = true)
    private String name;

    /**
     * Страна места проведения
     */
    @ApiModelProperty(value = "Страна места проведения курса", example = "Россия", allowEmptyValue = true)
    private String country;

    /**
     * Город места проведения
     */
    @ApiModelProperty(value = "Город места проведения курса", example = "Шерегеш", allowEmptyValue = true)
    private String city;

    public LocationSearchDto(){}

    public LocationSearchDto(String name, String country, String city) {
        this.name = name;
        this.country = country;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "LocationSearchDto{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
