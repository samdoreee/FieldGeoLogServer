package com.samdoree.fieldgeolog.GeoPoint.Controller;

import com.samdoree.fieldgeolog.GeoPoint.DTO.GeoPointRequestDTO;
import com.samdoree.fieldgeolog.GeoPoint.DTO.GeoPointResponseDTO;
import com.samdoree.fieldgeolog.GeoPoint.Service.GeoPointRegisterService;
import com.samdoree.fieldgeolog.GeoPoint.Service.GeoPointRemoveService;
import com.samdoree.fieldgeolog.GeoPoint.Service.GeoPointSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GeoPointController {

    private final GeoPointRegisterService geoPointRegisterService;
    private final GeoPointSearchService geoPointSearchService;
    private final GeoPointRemoveService geoPointRemoveService;

    // GPS 기록 생성
    @PostMapping("/api/articles")
    public GeoPointResponseDTO geoPointAdd(@Valid @RequestBody GeoPointRequestDTO geoPointRequestDTO) {
        return geoPointRegisterService.addGeoPoint(geoPointRequestDTO);
    }

    // GPS 기록 조회
    @GetMapping("/api/articles")
    public List<GeoPointResponseDTO> geoPointList() {
        return geoPointSearchService.findGeoPoints();
    }

    // GPS 기록 삭제
    @DeleteMapping("/api/articles/{articleId}")
    public Boolean geoPointRemove(@PathVariable Long articleId) {
        return geoPointRemoveService.removeGeoPoint(articleId);
    }
}
