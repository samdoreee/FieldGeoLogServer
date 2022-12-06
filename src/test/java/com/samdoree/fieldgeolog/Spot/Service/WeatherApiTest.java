package com.samdoree.fieldgeolog.Spot.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WeatherApiTest {

    @Autowired
    private WeatherApi weatherApi;

    @Test
    void getWeatherInfo_at_now() throws Exception {
        String weatherInfo = weatherApi.getWeatherInfo(LocalDateTime.now(), 36.6287, 127.4606);
        System.out.println("weatherInfo = " + weatherInfo);
    }

    @Test
    void getWeatherInfo_at_today_3AM() throws Exception {
        LocalDateTime testTime = LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.of(3, 0));
        String weatherInfo = weatherApi.getWeatherInfo(testTime, 36.6287, 127.4606);
        System.out.println("weatherInfo = " + weatherInfo);
    }

    @Test
    void normalize() {
        List<Integer> availableHours = Arrays.asList(2, 5, 8, 11, 14, 17, 20, 23);

        for (int hour = 0; hour < 24; hour++) {
            for (int min = 0; min < 60; min++) {
                LocalDateTime testTime = LocalDateTime.of(
                        LocalDateTime.now().toLocalDate(),
                        LocalTime.of(hour, min)
                );
                LocalDateTime result = WeatherApi.getRecentAvailableTime(testTime);
                System.out.println("result.getHour() = " + result.getHour());
                assertThat(availableHours.contains(result.getHour())).isTrue();
                assertThat(result.isBefore(testTime)).isTrue();
            }
        }
    }
}