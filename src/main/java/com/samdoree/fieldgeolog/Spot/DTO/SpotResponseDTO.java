package com.samdoree.fieldgeolog.Spot.DTO;

import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpotResponseDTO {

    private Long id;
    private Double latitude;
    private Double longitude;
    private LocalDateTime createDT;
    private LocalDateTime modifyDT;
    private String weatherInfo;
    private Integer strike;
    private String rockType;
    private String geoStructure;
    private Integer dip;
    private String direction;

    public SpotResponseDTO(Spot spot) {
        this.id = spot.getId();
        this.latitude = spot.getLatitude();
        this.longitude = spot.getLongitude();
        this.createDT = spot.getCreateDT();
        this.modifyDT = spot.getModifyDT();
        this.weatherInfo = spot.getWeatherInfo();
        this.strike = spot.getStrike();
        this.rockType = spot.getRockType();
        this.geoStructure = spot.getGeoStructure();
        this.dip = spot.getDip();
        this.direction = spot.getDirection();
    }

    public static SpotResponseDTO from(Spot spot) {
        return new SpotResponseDTO(spot);
    }
}
