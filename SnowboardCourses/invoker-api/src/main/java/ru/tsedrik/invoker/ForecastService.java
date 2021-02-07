package ru.tsedrik.invoker;

import java.time.LocalDate;
import java.util.Map;

public interface ForecastService {
    Map<LocalDate, Forecast> getForecastForThePeriod(String name, LocalDate startDate, LocalDate endDate);
}
