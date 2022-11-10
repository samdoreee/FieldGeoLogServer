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
    private LocalDateTime createDt;

    public SpotResponseDTO(Spot spot) {
        this.id = spot.getId();
        this.latitude = spot.getLatitude();
        this.longitude = spot.getLongitude();
        this.createDt = spot.getCreateDT();
    }

    public static SpotResponseDTO from(Spot spot) {
        return new SpotResponseDTO(spot);
    }
}
