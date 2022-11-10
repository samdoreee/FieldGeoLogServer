package com.samdoree.fieldgeolog.Spot.DTO;

import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
public class SpotRequestDTO {

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    private LocalDateTime createDT;

    public Spot toEntity() {
        return Spot.builder()
                .latitude(latitude)
                .longitude(longitude)
                .createDT(LocalDateTime.now())
                .build();
    }
}
