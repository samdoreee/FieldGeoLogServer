package com.samdoree.fieldgeolog.Spot.DTO;

import com.samdoree.fieldgeolog.Memo.DTO.MemoResponseDTO;
import com.samdoree.fieldgeolog.Spot.Entity.Spot;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
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
    private List<MemoResponseDTO> memoResponseDTOList;

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
        this.memoResponseDTOList = spot.getMemoList().stream().map(MemoResponseDTO::new).collect(Collectors.toList());
    }

    public static SpotResponseDTO from(Spot spot) {
        return new SpotResponseDTO(spot);
    }
}
