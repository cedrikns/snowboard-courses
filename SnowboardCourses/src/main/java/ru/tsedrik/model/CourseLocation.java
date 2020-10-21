package ru.tsedrik.model;

/**
 * CourseLocation представляет собой место проведения курса.
 * Каждый объект данного класса будет характеризоваться:
 * - названием
 * - местом нахождения
 */
public class CourseLocation implements Identifired<Long> {

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
        return "CourseLocation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
