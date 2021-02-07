package ru.tsedrik.controller.feign;

import org.springframework.cloud.openfeign.FeignClient;
import ru.tsedrik.invoker.ForecastResource;

@FeignClient(name = "publisher", url = "${forecast-service.url}")
public interface ForecastClient extends ForecastResource {

}
