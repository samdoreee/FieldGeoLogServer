package com.samdoree.fieldgeolog.GeoPoint.Entity;

import com.samdoree.fieldgeolog.GeoPoint.DTO.GeoPointRequestDTO;
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
public class GeoPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double latitude;     // 위도(가로 좌표)
    private Double longitude;    // 경도(세로 좌표)

    @CreatedDate
    private LocalDateTime createDT;  // 시간

    public static GeoPoint createGeoPoint(GeoPointRequestDTO geoPointRequestDTO) {
        return GeoPoint.builder()
                .latitude(geoPointRequestDTO.getLatitude())
                .longitude(geoPointRequestDTO.getLongitude())
                .createDT(LocalDateTime.now())
                .build();
    }

    @Builder
    private GeoPoint(Double latitude, Double longitude, LocalDateTime createDT) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.createDT = createDT;
    }

}
