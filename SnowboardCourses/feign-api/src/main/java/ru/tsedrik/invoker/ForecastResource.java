package ru.tsedrik.invoker;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.tsedrik.invoker.dto.ForecastDto;
import ru.tsedrik.invoker.dto.ForecastSearchDto;

import java.time.LocalDate;
import java.util.Map;

@RequestMapping("/api/v1/forecast")
public interface ForecastResource {

    @PostMapping
    Map<LocalDate, ForecastDto> getForecastForThePeriod(@RequestBody ForecastSearchDto forecastSearchDto);
}
