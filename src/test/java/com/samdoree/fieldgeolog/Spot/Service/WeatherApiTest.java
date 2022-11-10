package com.samdoree.fieldgeolog.Spot.Service;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class WeatherApiTest {

    @Test
    void restApiGetWeather() throws Exception {
        WeatherApi weatherApi = new WeatherApi();
        weatherApi.restApiGetWeather(LocalDateTime.now(), 12.3, 12.3);
    }
}