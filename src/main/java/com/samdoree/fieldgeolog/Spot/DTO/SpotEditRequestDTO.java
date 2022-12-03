package com.samdoree.fieldgeolog.Spot.DTO;

import lombok.Getter;

@Getter
public class SpotEditRequestDTO {

    private Integer strike;
    private String rockType;
    private String geoStructure;
    private Integer dip;
    private String direction;
}
