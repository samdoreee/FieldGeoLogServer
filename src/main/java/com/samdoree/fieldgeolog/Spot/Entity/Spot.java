package com.samdoree.fieldgeolog.Spot.Entity;

import com.samdoree.fieldgeolog.Spot.Service.WeatherApi;
import lombok.*;
import com.samdoree.fieldgeolog.Spot.DTO.SpotEditRequestDTO;
import com.samdoree.fieldgeolog.Spot.DTO.SpotInsertRequestDTO;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class Spot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //==  1. 자동입력 정보 ===//
    private Double latitude;     // 위도(가로 좌표)
    private Double longitude;    // 경도(세로 좌표)
    private Double X;
    private Double Y;

    @CreatedDate
    private LocalDateTime createDT;

    private String weatherInfo;     // 날씨정보

    //==  수동 입력 + 필수 정보 ===//
    private Integer strike;         // 주향

    @Column(name = "rock_type")
    private String rockType;        // 암종

    @Column(name = "geo_structure")
    private String geoStructure;    // 지질구조
    private Integer dip;            // 경사
    private String direction;


    public static Spot createFrom(SpotInsertRequestDTO spotInsertRequestDTO) throws Exception {
        return new Spot(spotInsertRequestDTO);
    }

    public static Spot createFrom(SpotEditRequestDTO spotEditRequestDTO) throws Exception {
        return new Spot(spotEditRequestDTO);
    }

    private Spot(SpotInsertRequestDTO spotRequestDTO) throws Exception {
        this.latitude = spotRequestDTO.getLatitude();
        this.longitude = spotRequestDTO.getLongitude();
        this.createDT = LocalDateTime.now();
        this.weatherInfo = WeatherApi.getWeatherInfo(createDT, latitude, longitude);
        this.strike = spotRequestDTO.getStrike();
        this.rockType = spotRequestDTO.getRockType();
        this.geoStructure = spotRequestDTO.getGeoStructure();
        this.dip = spotRequestDTO.getDip();
        this.direction = spotRequestDTO.getDirection();
    }

    private Spot(SpotEditRequestDTO spotRequestDTO) {
        this.strike = spotRequestDTO.getStrike();
        this.rockType = spotRequestDTO.getRockType();
        this.geoStructure = spotRequestDTO.getGeoStructure();
        this.dip = spotRequestDTO.getDip();
        this.direction = spotRequestDTO.getDirection();
    }

    public void modifySpot(SpotEditRequestDTO spotRequestDTO) {
        if (spotRequestDTO.getDirection() != null)
            this.strike = spotRequestDTO.getStrike();
        if (spotRequestDTO.getRockType() != null)
            this.rockType = spotRequestDTO.getRockType();
        if (spotRequestDTO.getGeoStructure() != null)
            this.geoStructure = spotRequestDTO.getGeoStructure();
        if (spotRequestDTO.getDip() != null)
            this.dip = spotRequestDTO.getDip();
        if (spotRequestDTO.getDirection() != null)
            this.direction = spotRequestDTO.getDirection();
    }
}