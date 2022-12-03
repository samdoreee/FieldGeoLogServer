package com.samdoree.fieldgeolog.Spot.DTO;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class SpotInsertRequestDTO {

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @NotNull
    private Integer strike;

    @NotBlank
    private String rockType;

    @NotBlank
    private String geoStructure;

    @NotNull
    private Integer dp;

    @NotNull
    private String direction;
}
