package com.samdoree.fieldgeolog.Spot.Entity;

import com.samdoree.fieldgeolog.Memo.Entity.Memo;
import com.samdoree.fieldgeolog.PersonalRecord.Entity.PersonalRecord;
import com.samdoree.fieldgeolog.Spot.Service.WeatherApi;
import lombok.*;
import com.samdoree.fieldgeolog.Spot.DTO.SpotEditRequestDTO;
import com.samdoree.fieldgeolog.Spot.DTO.SpotInsertRequestDTO;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class Spot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spot_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personalRecord_id")
    private PersonalRecord personalRecord;

    @OneToMany(mappedBy = "spot", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Memo> memoList = new ArrayList<>();

    private Boolean isValid;

    //==  1. 자동입력 정보 ===//
    private Double latitude;     // 위도(가로 좌표)
    private Double longitude;    // 경도(세로 좌표)
    private Double X;
    private Double Y;

    @CreatedDate
    private LocalDateTime createDT;
    @CreatedDate
    private LocalDateTime modifyDT;

    private String weatherInfo;     // 날씨정보

    //==  수동 입력 + 필수 정보 ===//
    private Integer strike;         // 주향

    @Column(name = "rock_type")
    private String rockType;        // 암종

    @Column(name = "geo_structure")
    private String geoStructure;    // 지질구조
    private Integer dip;            // 경사
    private String direction;

    public static Spot createFrom(PersonalRecord personalRecord, SpotInsertRequestDTO spotInsertRequestDTO, WeatherApi weatherApi) throws Exception {
        return new Spot(personalRecord, spotInsertRequestDTO, weatherApi);
    }

    public static Spot createFrom(SpotEditRequestDTO spotEditRequestDTO) throws Exception {
        return new Spot(spotEditRequestDTO);
    }

    private Spot(PersonalRecord personalRecord, SpotInsertRequestDTO spotRequestDTO, WeatherApi weatherApi) throws Exception {
        this.personalRecord = personalRecord;
        this.latitude = spotRequestDTO.getLatitude();
        this.longitude = spotRequestDTO.getLongitude();
        this.createDT = LocalDateTime.now();
        this.modifyDT = LocalDateTime.now();
        this.weatherInfo = weatherApi.getWeatherInfo(createDT, latitude, longitude);
        this.strike = spotRequestDTO.getStrike();
        this.rockType = spotRequestDTO.getRockType();
        this.geoStructure = spotRequestDTO.getGeoStructure();
        this.dip = spotRequestDTO.getDip();
        this.direction = spotRequestDTO.getDirection();
        this.isValid = true;
    }

    private Spot(SpotEditRequestDTO spotRequestDTO) {
        this.strike = spotRequestDTO.getStrike();
        this.rockType = spotRequestDTO.getRockType();
        this.geoStructure = spotRequestDTO.getGeoStructure();
        this.dip = spotRequestDTO.getDip();
        this.direction = spotRequestDTO.getDirection();
    }

    public void modifySpot(SpotEditRequestDTO spotRequestDTO) {
        this.modifyDT = LocalDateTime.now();
        if (spotRequestDTO.getDirection() != null)
            this.strike = spotRequestDTO.getStrike();
        if (spotRequestDTO.getRockType() != null)
            this.rockType = spotRequestDTO.getRockType();
        if (spotRequestDTO.getGeoStructure() != null)
            this.geoStructure = spotRequestDTO.getGeoStructure();
        if (spotRequestDTO.getDip() != null)
            this.dip = spotRequestDTO.getDip();
        if (spotRequestDTO.getDirection() != null)
            this.direction = spotRequestDTO.getDirection();
    }

    //== 유효성 필드 메서드 ==//
    public void markAsInvalid() {
        this.isValid = false;
    }

    public boolean isValid() {
        return isValid;
    }

    //== 연관관계 메서드 ==//
    public void addMemo(Memo memo) {
        memoList.add(memo);
        memo.belongToSpot(this);
    }

    public void removeMemo() {
        memoList.clear();
    }

    public void belongToPersonalRecord(PersonalRecord personalRecord) {
        this.personalRecord = personalRecord;
    }
}