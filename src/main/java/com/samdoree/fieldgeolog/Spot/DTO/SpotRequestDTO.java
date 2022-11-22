package com.samdoree.fieldgeolog.Spot.DTO;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class SpotRequestDTO {

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;
}
