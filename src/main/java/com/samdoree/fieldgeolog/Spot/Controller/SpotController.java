package com.samdoree.fieldgeolog.Spot.Controller;

import com.samdoree.fieldgeolog.Spot.DTO.SpotEditRequestDTO;
import com.samdoree.fieldgeolog.Spot.DTO.SpotInsertRequestDTO;
import com.samdoree.fieldgeolog.Spot.DTO.SpotResponseDTO;
import com.samdoree.fieldgeolog.Spot.Service.SpotModifyService;
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

    private final SpotRegisterService spotRegisterService;
    private final SpotSearchService spotSearchService;
    private final SpotModifyService spotModifyService;
    private final SpotRemoveService spotRemoveService;

    @PostMapping("/api/spots")
    public SpotResponseDTO addSpot(@Valid @RequestBody SpotInsertRequestDTO spotInsertRequestDTO) throws Exception {
        return spotRegisterService.addSpot(spotInsertRequestDTO);
    }

    @GetMapping("/api/spots")
    public List<SpotResponseDTO> getAllSpotList() {
        return spotSearchService.getAllSpotList();
    }

    @GetMapping("/api/spots/{spotId}")
    public SpotResponseDTO getOneSpot(@PathVariable Long spotId) {
        return spotSearchService.getOneSpot(spotId);
    }

    @PatchMapping("/api/spots/{spotId}")
    public SpotResponseDTO modifySpot(@PathVariable Long spotId, @Valid @RequestBody SpotEditRequestDTO spotEditRequestDTO) throws Exception {
        return spotModifyService.modifySpot(spotId, spotEditRequestDTO);
    }

    @DeleteMapping("/api/spots/{spotId}")
    public Boolean removeSpot(@PathVariable Long spotId) {
        return spotRemoveService.removeSpot(spotId);
    }
}
