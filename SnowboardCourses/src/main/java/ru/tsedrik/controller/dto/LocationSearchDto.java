package ru.tsedrik.controller.dto;

/**
 * Класс для передачи параметров поиска сущности Location
 */
public class LocationSearchDto {

    /**
     * Название места проведения
     */
    private String name;

    /**
     * Страна места проведения
     */
    private String country;

    /**
     * Город места проведения
     */
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
}
