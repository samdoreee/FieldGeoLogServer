package com.samdoree.fieldgeolog.Spot.Controller;

import com.samdoree.fieldgeolog.Spot.DTO.SpotRequestDTO;
import com.samdoree.fieldgeolog.Spot.DTO.SpotResponseDTO;
import com.samdoree.fieldgeolog.Spot.Service.SpotRegisterService;
import com.samdoree.fieldgeolog.Spot.Service.SpotRemoveService;
import com.samdoree.fieldgeolog.Spot.Service.SpotSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SpotController {

    private final SpotRegisterService SpotRegisterService;
    private final SpotSearchService SpotSearchService;
    private final SpotRemoveService SpotRemoveService;

    // GPS 기록 생성
    @PostMapping("/api/spots")
    public SpotResponseDTO addSpot(@Valid @RequestBody SpotRequestDTO SpotRequestDTO) {
        return SpotRegisterService.addSpot(SpotRequestDTO);
    }

    // GPS 기록 조회
    @GetMapping("/api/spots")
    public List<SpotResponseDTO> getAllSpotList() {
        return SpotSearchService.getAllSpotList();
    }

    // GPS 기록 삭제
    @DeleteMapping("/api/spots/{spotId}")
    public Boolean removeSpot(@PathVariable Long spotId) {
        return SpotRemoveService.removeSpot(spotId);
    }
}
