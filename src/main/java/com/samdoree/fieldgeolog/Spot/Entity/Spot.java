package com.samdoree.fieldgeolog.Spot.Entity;

import com.samdoree.fieldgeolog.Spot.DTO.SpotRequestDTO;
import com.samdoree.fieldgeolog.Spot.Service.WeatherApi;
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

    private Double latitude;        // 위도(가로 좌표)
    private Double longitude;       // 경도(세로 좌표)
//    private LatXLngY latXLngY;      // 위도, 경도, X, Y
    private Double X;
    private Double Y;

    @CreatedDate
    private LocalDateTime createDT;

    private String weatherInfo;     // 날씨정보

    public static Spot createFrom(SpotRequestDTO spotRequestDTO) throws Exception {
        return new Spot(spotRequestDTO);
    }

    private Spot(SpotRequestDTO spotRequestDTO) throws Exception {
        this.latitude = spotRequestDTO.getLatitude();
        this.longitude = spotRequestDTO.getLongitude();
        this.createDT = LocalDateTime.now();
        Double[] XY = convertGRID_GPS(latitude,longitude);
        this.X = XY[0];
        this.Y = XY[1];
        this.weatherInfo = new WeatherApi().restApiGetWeather(createDT, X, Y);
    }

    // 위도, 경도 => grid X,Y좌표로 변환하는 모듈 구현
    public Double[] convertGRID_GPS(double latitude, double longitude) {
        double RE = 6371.00877; // 지구 반경(km)
        double GRID = 5.0;      // 격자 간격(km)
        double SLAT1 = 30.0;    // 투영 위도1(degree)
        double SLAT2 = 60.0;    // 투영 위도2(degree)
        double OLON = 126.0;// 기준점 경도(degree)
        double OLAT = 38.0; // 기준점 위도(degree)
        double XO = 43;     // 기준점 X좌표(GRID)
        double YO = 136;    // 기준점 Y좌표(GRID)

        double DEGRAD = Math.PI / 180.0;

        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon = OLON * DEGRAD;
        double olat = OLAT * DEGRAD;

        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);

        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;

        double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);

        double ra = Math.tan(Math.PI * 0.25 + (latitude) * DEGRAD * 0.5);
        ra = re * sf / Math.pow(ra, sn);

        double theta = longitude * DEGRAD - olon;
        if (theta > Math.PI) theta -= 2.0 * Math.PI;
        if (theta < -Math.PI) theta += 2.0 * Math.PI;
        theta *= sn;

        Double X = Math.floor(ra * Math.sin(theta) + XO + 0.5);
        Double Y = Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);
        Double[] XY = new Double[2];
        XY[0] = X;
        XY[1] = Y;
        return XY;
    }
}