package ru.tsedrik.resource.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Модель для создания места проведения курсов")
public class LocationDto {

    /**
     * Идентификатор места проведения курса
     */
    @ApiModelProperty(value = "Идентификатор места проведения курса", example = "1608061807884", allowEmptyValue = true)
    private Long id;

    /**
     * Название места проведения
     */
    @ApiModelProperty(value = "Название места проведения курса", example = "Шерегеш", required = true)
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

    public LocationDto(){}

    public LocationDto(Long id, String name, String country, String city) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
