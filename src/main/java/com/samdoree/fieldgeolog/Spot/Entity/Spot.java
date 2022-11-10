package com.samdoree.fieldgeolog.Spot.Entity;

import com.samdoree.fieldgeolog.Spot.DTO.SpotRequestDTO;
import com.samdoree.fieldgeolog.Spot.Service.WeatherApi;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Spot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double latitude;     // 위도(가로 좌표)
    private Double longitude;    // 경도(세로 좌표)

    @CreatedDate
    private LocalDateTime createDT;

    private String weatherInfo; // 날씨 정보

    @Builder
    private Spot(Double latitude, Double longitude, LocalDateTime createDT) throws Exception {
        this.latitude = latitude;
        this.longitude = longitude;
        this.createDT = createDT;

        WeatherApi weatherApi = new WeatherApi();
        this.weatherInfo = weatherApi.restApiGetWeather(createDT, latitude, longitude);
    }

    public Spot(SpotRequestDTO SpotRequestDTO) {
        this.latitude = SpotRequestDTO.getLatitude();
        this.longitude = SpotRequestDTO.getLongitude();
        this.createDT = SpotRequestDTO.getCreateDT();
    }
}