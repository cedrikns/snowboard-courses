package ru.tsedrik.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Location представляет собой место проведения курса.
 * Каждый объект данного класса будет характеризоваться:
 * - названием
 * - местом нахождения
 */
@Entity
@Table(name = "location")
public class Location extends CreateAtIdentified implements Identifired<Long> {

    /**
     * Идентификатор места проведения курса
     */
    @Id
    private Long id;

    /**
     * Название места проведения
     */
    @Column(nullable = false)
    private String name;

    /**
     * Страна места проведения
     */
    private String country;

    /**
     * Город места проведения
     */
    private String city;

    public Location() {}

    public Location(Long id, String name, String country, String city) {
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

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
