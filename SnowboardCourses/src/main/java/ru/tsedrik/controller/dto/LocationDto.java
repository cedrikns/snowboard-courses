package ru.tsedrik.controller.dto;

public class LocationDto {

    /**
     * Идентификатор места проведения курса
     */
    private Long id;

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
