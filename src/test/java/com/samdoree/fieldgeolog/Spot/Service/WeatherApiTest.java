package com.samdoree.fieldgeolog.Spot.Service;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

class WeatherApiTest {

    @Test
    void getWeatherInfo_at_now() throws Exception {
        Double[] XY = WeatherApi.convertGRID_GPS(36.6287, 127.4606);
        String weatherInfo = WeatherApi.getWeatherInfo(LocalDateTime.now(), XY[0], XY[1]);
        System.out.println("weatherInfo = " + weatherInfo);
    }

    @Test
    void getWeatherInfo_at_today_3AM() throws Exception {
        Double[] XY = WeatherApi.convertGRID_GPS(36.6287, 127.4606);
        String weatherInfo = WeatherApi.getWeatherInfo(LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.of(3, 0)), XY[0], XY[1]);
        System.out.println("weatherInfo = " + weatherInfo);
    }
}