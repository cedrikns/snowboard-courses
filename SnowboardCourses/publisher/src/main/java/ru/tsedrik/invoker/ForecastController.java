package ru.tsedrik.invoker;

import org.springframework.web.bind.annotation.RestController;
import ru.tsedrik.invoker.dto.ForecastDto;
import ru.tsedrik.invoker.dto.ForecastSearchDto;

import java.time.LocalDate;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * Реализация интерфейса ForecastService для возврата погоды
 */
@RestController
public class ForecastController implements ForecastResource {

    @Override
    public Map<LocalDate, ForecastDto> getForecastForThePeriod(ForecastSearchDto forecastSearchDto) {
        Map<LocalDate, ForecastDto> forecasts = new TreeMap<>();
        Random random = new Random();

        LocalDate tmpDate = forecastSearchDto.getBeginDate();
        while (!tmpDate.isAfter(forecastSearchDto.getEndDate())){
            ForecastDto forecast = new ForecastDto(
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
