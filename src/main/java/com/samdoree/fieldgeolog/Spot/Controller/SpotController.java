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
@RequestMapping("/api/personalRecords/{personalRecordId}/spots")
@RequiredArgsConstructor
public class SpotController {

    private final SpotRegisterService spotRegisterService;
    private final SpotSearchService spotSearchService;
    private final SpotModifyService spotModifyService;
    private final SpotRemoveService spotRemoveService;


    @PostMapping()
    public SpotResponseDTO addSpot(@PathVariable Long personalRecordId, @Valid @RequestBody SpotInsertRequestDTO spotInsertRequestDTO) throws Exception {
        return spotRegisterService.addSpot(personalRecordId, spotInsertRequestDTO);
    }

    @GetMapping()
    public List<SpotResponseDTO> getAllSpotList(@PathVariable Long personalRecordId) {
        return spotSearchService.getAllSpotList(personalRecordId);
    }

    @GetMapping("/{spotId}")
    public SpotResponseDTO getOneSpot(@PathVariable Long personalRecordId, @PathVariable Long spotId) {
        return spotSearchService.getOneSpot(personalRecordId, spotId);
    }

    @PatchMapping("/{spotId}")
    public SpotResponseDTO modifySpot(@PathVariable Long personalRecordId, @PathVariable Long spotId, @Valid @RequestBody SpotEditRequestDTO spotEditRequestDTO) throws Exception {
        return spotModifyService.modifySpot(personalRecordId, spotId, spotEditRequestDTO);
    }

    @DeleteMapping("/{spotId}")
    public Boolean removeSpot(@PathVariable Long personalRecordId, @PathVariable Long spotId) throws Exception {
        return spotRemoveService.removeSpot(personalRecordId, spotId);
    }
}
