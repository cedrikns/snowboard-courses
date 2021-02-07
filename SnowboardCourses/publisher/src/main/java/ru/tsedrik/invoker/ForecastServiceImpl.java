package ru.tsedrik.invoker;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalUnit;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * Реализация интерфейса ForecastService для возврата погоды
 */
@Service
public class ForecastServiceImpl implements ForecastService{

    @Override
    public Map<LocalDate, Forecast> getForecastForThePeriod(String name, LocalDate startDate, LocalDate endDate) {
        Map<LocalDate, Forecast> forecasts = new TreeMap<>();
        Random random = new Random();

        LocalDate tmpDate = startDate;
        while (!tmpDate.isAfter(endDate)){
            Forecast forecast = new Forecast(
                    random.nextInt(0 + 25) -25,
                    random.nextInt(99 - 10) + 10,
                    random.nextInt(20 - 1) + 1
            );

            forecasts.put(tmpDate, forecast);
            tmpDate = tmpDate.plusDays(1);
        }

        return forecasts;
    }
}
