package com.samdoree.fieldgeolog.GeoPoint.DTO;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class GeoPointRequestDTO {

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

}
