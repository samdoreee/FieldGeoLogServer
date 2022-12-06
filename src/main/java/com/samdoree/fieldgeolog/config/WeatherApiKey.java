package com.samdoree.fieldgeolog.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class WeatherApiKey {
    @Value("${auth-key}")
    private String key;
}