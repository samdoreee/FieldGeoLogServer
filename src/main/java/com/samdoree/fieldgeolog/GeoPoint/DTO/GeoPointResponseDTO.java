package com.samdoree.fieldgeolog.GeoPoint.DTO;

import com.samdoree.fieldgeolog.GeoPoint.Entity.GeoPoint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GeoPointResponseDTO {

    private Long id;
    private Double latitude;
    private Double longitude;
    private LocalDateTime createDt;

    public GeoPointResponseDTO(GeoPoint geoPoint) {
        this.id = geoPoint.getId();
        this.latitude = geoPoint.getLatitude();
        this.longitude = geoPoint.getLongitude();
        this.createDt = geoPoint.getCreateDT();
    }

    public static GeoPointResponseDTO from(GeoPoint geoPoint) {
        return new GeoPointResponseDTO(geoPoint);
    }
}
