package com.samdoree.fieldgeolog.Spot.Entity;

import com.samdoree.fieldgeolog.Spot.DTO.SpotRequestDTO;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class Spot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double latitude;     // 위도(가로 좌표)
    private Double longitude;    // 경도(세로 좌표)

    @CreatedDate
    private LocalDateTime createDT;  // 시간

    static public Spot createFrom(SpotRequestDTO spotRequestDTO) {
        return new Spot(spotRequestDTO);
    }

    private Spot(SpotRequestDTO spotRequestDTO) {
        this.latitude = spotRequestDTO.getLatitude();
        this.longitude = spotRequestDTO.getLongitude();
        this.createDT = LocalDateTime.now();
    }
}