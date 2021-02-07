package ru.tsedrik.invoker;

import java.io.Serializable;

/**
 * Класс для возврата прогноза погоды
 */
public class Forecast implements Serializable{

    /**
     * Средняя дневная температура по дням
     */
    private Integer temperature;

    /**
     * Средняя дневная влажность воздуха
     */
    private Integer airHumidity;

    /**
     * Средняя скорость ветра
     */
    private Integer windSpeed;

    public Forecast() {
    }

    public Forecast(Integer temperature, Integer airHumidity, Integer windSpeed) {
        this.temperature = temperature;
        this.airHumidity = airHumidity;
        this.windSpeed = windSpeed;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getAirHumidity() {
        return airHumidity;
    }

    public void setAirHumidity(Integer airHumidity) {
        this.airHumidity = airHumidity;
    }

    public Integer getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Integer windSpeed) {
        this.windSpeed = windSpeed;
    }
}
