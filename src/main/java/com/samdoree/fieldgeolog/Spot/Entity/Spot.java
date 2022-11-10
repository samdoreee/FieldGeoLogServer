package com.samdoree.fieldgeolog.Spot.Entity;

import com.samdoree.fieldgeolog.Spot.DTO.SpotRequestDTO;
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
    private LocalDateTime createDT;  // 시간

    public static Spot createGeoPoint(SpotRequestDTO spotRequestDTO) {
        return Spot.builder()
                .latitude(spotRequestDTO.getLatitude())
                .longitude(spotRequestDTO.getLongitude())
                .createDT(LocalDateTime.now())
                .build();
    }

    @Builder
    private Spot(Double latitude, Double longitude, LocalDateTime createDT) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.createDT = createDT;
    }

}
